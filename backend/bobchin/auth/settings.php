<?php
ini_set('include_path', '/volume2/web/bobchin/auth/google-api-php-client'); //set google api path
include("dbconnect.php");
include("../api/messaging/postmessage.php");

require_once 'vendor/autoload.php';

// init configuration
$clientID = '278850967870-sgc0n7q3pcj3psidv3aqnomimuv15hua.apps.googleusercontent.com';
$clientSecret = 'HyS5MBI7hxNnMr47WK1siech';
$redirectUri = 'http://bobchin.cf/auth/rcvcode.php';
  
// create Client Request to access Google API
$client = new Google_Client();
$client->setClientId($clientID);
$client->setClientSecret($clientSecret);
$client->setRedirectUri($redirectUri);
$client->setAccessType('offline');
$client->setApprovalPrompt('force');
$client->addScope("email");
$client->addScope("profile");

if(isset($_GET['token'])){
    $query = "SELECT refreshtoken,last_accesstoken,last_renew FROM user WHERE accesstoken='$_GET[token]'";
    $result = mysqli_query($con, $query);
    
    $row = mysqli_fetch_array($result);
    $refreshtoken = $row['refreshtoken'];
    $last_accesstoken = $row['last_accesstoken'];

    $datetime = new DateTime();
    $last_renew = new DateTime($row['last_renew']);
    if($datetime->getTimeStamp()-$last_renew->getTimeStamp()>=3500) $expired=true;
}
else if(isset($_POST['token'])){
    $query = "SELECT refreshtoken,last_accesstoken,last_renew FROM user WHERE accesstoken='$_POST[token]'";
    $result = mysqli_query($con, $query);
    $row = mysqli_fetch_array($result);
    $refreshtoken = $row['refreshtoken'];
    $last_accesstoken = $row['last_accesstoken'];

    $datetime = new DateTime();
    $last_renew = new DateTime($row['last_renew']);
    if($datetime->getTimeStamp()-$last_renew->getTimeStamp()>=3500) $expired=true;
}

function RefreshToken($con, $client, $refreshtoken){
    $new_access_token = $client->fetchAccessTokenWithRefreshToken($refreshtoken)['access_token'];
    $client->setAccessToken($new_access_token);
    
    $google_oauth = new Google_Service_Oauth2($client);
    $google_account_info = $google_oauth->userinfo->get();
    $email = $google_account_info->email;
    $datetime = date("Y-m-d H:i:s", time());
    $query = "UPDATE user SET last_accesstoken='$new_access_token',last_renew='$datetime' WHERE email='$email'";
    mysqli_query($con, $query);
    return $new_access_token;
}
?>