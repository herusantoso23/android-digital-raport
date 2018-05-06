<?php
require_once('../config.php');

header("Content-Type:application/json");
//$data = json_decode(file_get_contents('php://input'), true);

if($_SERVER['REQUEST_METHOD']=='GET'){

  $response = array();

  $nis = $_GET['nis'];

  $sql = "SELECT * FROM user WHERE nis = '$nis'";
  $res = mysqli_query($con, $sql);
  $check = mysqli_fetch_array(mysqli_query($con,$sql));
  if(isset($check)){
    $result = array();

    while($row = mysqli_fetch_array($res)){
      array_push($result, array(
        'nis' => $row[1],
        'nama' => $row[2],
        'kelas' => $row[3],
        'no_hp' => $row[4],
        'alamat' => $row[5],
        'photo' => $row[7]
      ));
    }

    echo json_encode(array(
      "message" => 1,
      "result" => $result
    ));

  } else {
    $response["message"] = 0;
    $response["result"] = "User tidak ditemukan!";
    echo json_encode($response);
  }

  mysqli_close($con);
} else {
  $response["message"] = 0;
  $response["result"] = "Oops ! Coba lagi!";
  echo json_encode($response);
}


 ?>
