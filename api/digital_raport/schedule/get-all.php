<?php
require_once('../config.php');

header("Content-Type:application/json");
//$data = json_decode(file_get_contents('php://input'), true);

if($_SERVER['REQUEST_METHOD']=='GET'){

  $response = array();

  $sql = "SELECT
                kelas.namaKelas,kelas.jurusan,
                mapel.kodeMapel,mapel.namaMapel,
                jadwal.waktu,
                lecturer.nama AS guru
          FROM jadwal
          JOIN kelas ON kelas.id = jadwal.idKelas
          JOIN mapel ON mapel.id = jadwal.idMapel
          JOIN lecturer ON jadwal.idLecturer = lecturer.id";

  $res = mysqli_query($con, $sql);
  $check = mysqli_fetch_array(mysqli_query($con,$sql));
  if(isset($check)){
    $result = array();

    while($row = mysqli_fetch_array($res)){
      array_push($result, array(
        'nama_kelas' => $row[0],
        'jurusan' => $row[1],
        'kode_mapel' => $row[2],
        'nama_mapel' => $row[3],
        'waktu' => $row[4],
        'guru' => $row[5]
      ));
    }

    echo json_encode(array(
      "message" => 1,
      "result" => $result
    ));

  } else {
    $response["message"] = 1;
    $response["result"] = "Jadwal tidak ditemukan!";
    echo json_encode($response);
  }

  mysqli_close($con);
} else {
  $response["message"] = 0;
  $response["result"] = "Oops ! Coba lagi!";
  echo json_encode($response);
}


 ?>
