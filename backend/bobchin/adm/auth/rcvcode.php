<?php
session_start();
include_once('settings.php');
$_datetime = date("Y-m-d H:i:s", time());

if($expired) {
    $_token = $last_accesstoken = RefreshToken($con, $client, $refreshtoken);
}

if (isset($_GET['code'])) {
    $token = $client->fetchAccessTokenWithAuthCode($_GET['code']);
    $client->setAccessToken($token['access_token']);
    $_SESSION['atoken'] = $token['access_token'];

    $google_oauth = new Google_Service_Oauth2($client);
    $google_account_info = $google_oauth->userinfo->get();
    $email = $google_account_info->email;
	$name = $google_account_info->name;
    $id = $google_account_info->id;
    $picture = $google_account_info->picture;

    $query = "SELECT count(*) as cnt FROM user WHERE email='$email'";
    $result = mysqli_query($con, $query);
    $cnt = mysqli_fetch_assoc($result)['cnt'];

    $query = "SELECT * FROM user WHERE email='$email'";
    $result = mysqli_query($con, $query);
    $row = mysqli_fetch_assoc($result);

    if($cnt > 0){
		$query = "UPDATE user SET userid = '$id',accesstoken='$token[access_token]',name='$name',last_accesstoken='$token[access_token]',last_renew='$_datetime' WHERE email='$email'";
    	mysqli_query($con, $query);
    }
    echo "<script>window.location='../index.php';</script>";


} else if(!isset($_SESSION['atoken'])) {
    echo "<div class='text-center'><a class='btn btn-outline-dark' href='".$client->createAuthUrl()."'>Google Login</a></div>";
    exit;
}
?>