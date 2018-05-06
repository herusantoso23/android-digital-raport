<?php
require_once('../config.php');

header("Content-Type:application/json");

if($_SERVER['REQUEST_METHOD'] == 'GET'){
  $sql = "SELECT * FROM semester";
  $res = mysqli_query($con, $sql);
  $result = array();

  while($row = mysqli_fetch_array($res)){
    array_push($result, array(
      'semester' => $row[1]
    ));
  }

  echo json_encode(array(
    "message" => 1,
    "result" => $result
  ));
  mysqli_close($con);
}
 ?>
