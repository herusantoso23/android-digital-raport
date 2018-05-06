<?php
 require_once('../config.php');

 header("Content-Type:application/json");

  $part = "./uploads/";
  $filename = "img".rand(9,9999).".jpg";
  $response = array();
  if($_SERVER['REQUEST_METHOD'] == "POST"){
  	if($_FILES['imageupload']){
  		$destinationfile = $part.$filename;
  		if(move_uploaded_file($_FILES['imageupload']['tmp_name'], $destinationfile)){
        $response["message"] = 1;
        $response["result"] = "Photo berhasil di update!";
        echo json_encode($response);
  		} else {
        $response["message"] = 0;
        $response["result"] = "Gagal Upload!";
        echo json_encode($response);
  		}
  	} else{
      $response["message"] = 0;
      $response["result"] = "Request Error!";
      echo json_encode($response);
  	}
  } else {
    $response["message"] = 0;
    $response["result"] = "Request Tidak Valid!";
    echo json_encode($response);
  }

?>
