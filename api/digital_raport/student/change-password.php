<?php
require_once('../config.php');

header("Content-Type:application/json");
$data = json_decode(file_get_contents('php://input'), true);

if($_SERVER['REQUEST_METHOD']=='POST'){

  $response = array();

  $nis = $data['nis'];
  $oldPassword = $data['old_pass'];
  $newPassword = $data['new_pass'];
  $confirmPassword = $data['confirm_pass'];

  $encodePassword = md5($oldPassword);

  $sql = "SELECT * FROM user WHERE nis = '$nis'";
  $check = mysqli_fetch_array(mysqli_query($con,$sql));
  if(isset($check)){
    $sqlPass = "SELECT * FROM user WHERE nis = '$nis' AND password = '$encodePassword'";
    $checkPass = mysqli_fetch_array(mysqli_query($con,$sqlPass));

    $encodeNewPassword = md5($newPassword);

    if(isset($checkPass)){
      if($newPassword == $confirmPassword){
        $sql = "UPDATE user SET password = '$encodeNewPassword' WHERE nis = '$nis'";
        if(mysqli_query($con,$sql)){
          $response["message"] = 1;
          $response["result"] = "Password berhasil di update!";
          echo json_encode($response);
        } else {
          $response["message"] = 0;
          $response["result"] = "Gagal update";
          echo json_encode($response);
        }

      } else {
        $response["message"] = 0;
        $response["result"] = "Konfirmasi password baru tidak cocok!";
        echo json_encode($response);
      }

    } else {
      $response["message"] = 0;
      $response["result"] = "Password lama tidak cocok!";
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
