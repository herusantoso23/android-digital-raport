<?php
require_once('../config.php');
session_start();

header("Content-Type:application/json");
$data = json_decode(file_get_contents('php://input'), true);

if($_SERVER['REQUEST_METHOD']=='POST'){

  $response = array();

  $username = $data['username'];
  $password = $data['password'];

  $encodePassword = md5($password);

  $sql = "SELECT * FROM user WHERE nis = '$username' AND password = '$encodePassword'";
  $res = mysqli_query($con, $sql);
  $check = mysqli_fetch_array(mysqli_query($con,$sql));
  if(isset($check)){
    $result = array();

    while($row = mysqli_fetch_array($res)){
      array_push($result, array(
        'id' => $row[0],
        'nis' => $row[1],
        'nama' => $row[2],
        'kelas' => $row[3],
        'no_hp' => $row[4],
        'alamat' => $row[5]
      ));
    }

    echo json_encode(array(
      "message" => 1,
      "result" => $result
    ));

  } else {
    $response["message"] = 0;
    $response["result"] = "username atau password tidak valid!";
    echo json_encode($response);
  }

  mysqli_close($con);
} else {
  $response["message"] = 0;
  $response["result"] = "Oops ! Coba lagi!";
  echo json_encode($response);
}


 ?>
