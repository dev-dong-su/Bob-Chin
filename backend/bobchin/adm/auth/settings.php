<?php
ini_set('include_path', '/volume2/web/bobchin/auth/google-api-php-client'); //set google api path
include("dbconnect.php");

require_once 'vendor/autoload.php';

// init configuration
$clientID = '278850967870-sgc0n7q3pcj3psidv3aqnomimuv15hua.apps.googleusercontent.com';
$clientSecret = 'HyS5MBI7hxNnMr47WK1siech';
$redirectUri = 'http://bobchin.cf/adm/auth/rcvcode.php';
  
// create Client Request to access Google API
$client = new Google_Client();
$client->setClientId($clientID);
$client->setClientSecret($clientSecret);
$client->setRedirectUri($redirectUri);
$client->setAccessType('offline');
$client->setApprovalPrompt('force');
$client->addScope("email");
$client->addScope("profile");

if(isset($_SESSION['atoken'])){
    $query = "SELECT refreshtoken,last_accesstoken,last_renew,auth_level FROM user WHERE accesstoken='$_SESSION[atoken]'";
    $result = mysqli_query($con, $query);
    
    $row = mysqli_fetch_array($result);
    if($row['auth_level'] < 2) {session_destroy(); echo "<script>alert('관리자만 접근할 수 있습니다.');window.location='logout.php';</script>";exit;}
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
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<script src="fontawesome/js/all.js"></script>
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
  