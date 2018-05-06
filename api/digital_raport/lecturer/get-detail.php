<?php
require_once('../config.php');

header("Content-Type:application/json");
//$data = json_decode(file_get_contents('php://input'), true);

if($_SERVER['REQUEST_METHOD']=='GET'){

  $response = array();

  $nip = $_GET['nip'];

  $sql = "SELECT * FROM lecturer WHERE nip = '$nip'";
  $res = mysqli_query($con, $sql);
  $check = mysqli_fetch_array(mysqli_query($con,$sql));
  if(isset($check)){
    $result = array();

    while($row = mysqli_fetch_array($res)){
      array_push($result, array(
        'nip' => $row[1],
        'nama' => $row[2],
        'mapel' => $row[3],
        'no_hp' => $row[4],
        'alamat' => $row[5],
        'photo' => $row[6]
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
