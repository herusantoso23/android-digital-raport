<?php
require_once('../config.php');

header("Content-Type:application/json");
$data = json_decode(file_get_contents('php://input'), true);

if($_SERVER['REQUEST_METHOD']=='POST'){

  $response = array();

  $nis = $data['nis'];
  $kelas = $data['kelas'];
  $noHp = $data['no_hp'];
  $alamat = $data['alamat'];

  $sql = "SELECT * FROM user WHERE nis = '$nis'";
  $check = mysqli_fetch_array(mysqli_query($con,$sql));
  if(isset($check)){
    $sql = "UPDATE user SET kelas='$kelas' , noHp='$noHp' , alamat ='$alamat' WHERE nis = '$nis'";
    if(mysqli_query($con,$sql)){
      $response["message"] = 1;
      $response["result"] = "Profile berhasil di update!";
      echo json_encode($response);
    } else {
      $response["message"] = 0;
      $response["result"] = "Oops ! Coba lagi!";
      echo json_encode($response);
    }
  } else {
    $response["message"] = 0;
    $response["result"] = "User tnisak ditemukan!";
    echo json_encode($response);
  }

  mysqli_close($con);
} else {
  $response["message"] = 0;
  $response["result"] = "Oops ! Coba lagi!";
  echo json_encode($response);
}


 ?>
