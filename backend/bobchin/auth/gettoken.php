<?
include_once('settings.php');

$token = $client->fetchAccessTokenWithAuthCode($_GET['authcode']);
echo $token['access_token'];
?>