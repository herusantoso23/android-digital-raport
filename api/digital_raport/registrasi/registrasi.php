<?php
require_once('../config.php');

header("Content-Type:application/json");
$data = json_decode(file_get_contents('php://input'), true);

if($_SERVER['REQUEST_METHOD']=='POST'){

  $response = array();

  $nis = $data['nis'];
  $nama = $data['nama'];
  $kelas = $data['kelas'];
  $noHp = $data['no_hp'];
  $alamat = $data['alamat'];
  $password = $data['password'];

  $encodePassword = md5($password);

  $sql = "SELECT * FROM user WHERE nis = '$nis'";
  $check = mysqli_fetch_array(mysqli_query($con,$sql));
  if(isset($check)){
    $response["message"] = 0;
    $response["result"] = "Nomor induk sekolah sudah terdaftar!";
    echo json_encode($response);
  } else {
    $sql = "INSERT INTO user(id, nis, nama, kelas, noHp, alamat,password) VALUES (null,'$nis','$nama','$kelas','$noHp','$alamat','$encodePassword')";
    if(mysqli_query($con,$sql)){
      $response["message"] = 1;
      $response["result"] = "Registrasi berhasil!";
      echo json_encode($response);
    } else {
      $response["message"] = 0;
      $response["result"] = "Oops ! Coba lagi!";
      echo json_encode($response);
    }
  }

  mysqli_close($con);
} else {
  $response["message"] = 0;
  $response["result"] = "Oops ! Coba lagi!";
  echo json_encode($response);
}


 ?>
