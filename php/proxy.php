<?php

$action = $_GET['action'];
$data = $_GET['data'];



$handle = file_get_contents("http://localhost:8080/scicrop-be-web/TraitifyConnector?action=".$action."&data=".$data);




header('Content-Type: application/json');
echo ($handle);

?>
