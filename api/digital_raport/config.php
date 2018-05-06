<?php
define('HOST', 'localhost');
define('USER', 'root');
define('PASS', '');
define('DB', 'db_digital_raport');
define ('SITE_ROOT', realpath(dirname(__FILE__)));

$con = mysqli_connect(HOST,USER,PASS,DB) or die ("Unable to Connect");

 ?>
