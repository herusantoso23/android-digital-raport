<?php
require_once('../config.php');

header("Content-Type:application/json");
//$data = json_decode(file_get_contents('php://input'), true);

if($_SERVER['REQUEST_METHOD']=='GET'){

  $sql = "SELECT * FROM profile_sekolah ";
  $res = mysqli_query($con, $sql);
  $check = mysqli_fetch_array(mysqli_query($con,$sql));
  if(isset($check)){
    $result = array();

    while($row = mysqli_fetch_array($res)){
      array_push($result, array(
        'id' => $row[0],
        'nama' => $row[1],
        'alamat' => $row[2],
        'telp' => $row[3],
        'email' => $row[4],
        'fax' => $row[5],
        'gambar' => $row[6],
        'jumlah_siswa' => $row[7]
      ));
    }

    echo json_encode(array(
      "message" => 1,
      "result" => $result
    ));

  } else {
    $response["message"] = 1;
    $response["result"] = "Sekolah tidak ditemukan!";
    echo json_encode($response);
  }

  mysqli_close($con);
} else {
  $response["message"] = 0;
  $response["result"] = "Oops ! Coba lagi!";
  echo json_encode($response);
}


 ?>
