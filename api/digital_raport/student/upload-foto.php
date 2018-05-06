<?php
 require_once('../config.php');

 header("Content-Type:application/json");

 $upload_path = 'uploads/';
 $upload_url = 'digital_raport/'.$upload_path;

 $response = array();

 if($_SERVER['REQUEST_METHOD']=='POST'){

   if(isset($_POST['nis']) and isset($_FILES['image']['name'])){

   $nis = $_POST['nis'];

   $fileinfo = pathinfo($_FILES['image']['name']);

   $extension = $fileinfo['extension'];

   $file_url = $upload_url . $nis . '.' . $extension;

   $file_path = $upload_path . $nis . '.'. $extension;

   try{
     move_uploaded_file($_FILES['image']['tmp_name'], SITE_ROOT.'/'.$file_path);
     $sql = "UPDATE user SET photo='$file_url' WHERE nis = '$nis'";

     if(mysqli_query($con,$sql)){
       $response["message"] = 1;
       $response["result"] = $file_url;

     }
   } catch(Exception $e){
     $response["message"] = 1;
     $response["result"] = $e->getMessage();
   }
   echo json_encode($response);

   mysqli_close($con);
   } else {
     $response['message']=true;
     $response['result']='Please choose a file';
   }
 }
