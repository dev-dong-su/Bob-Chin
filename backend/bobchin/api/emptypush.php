<?
include_once('../auth/settings.php');
if(!isset($last_accesstoken)) {echo "Unauthorized"; exit;}
$client->setAccessToken($last_accesstoken);
if($expired) {
    $_token = $last_accesstoken = RefreshToken($con, $client, $refreshtoken);
}

$google_oauth = new Google_Service_Oauth2($client);
try{
    $google_account_info = $google_oauth->userinfo->get();
    $email = $google_account_info->email;
    $name = $google_account_info->name;
    $id = $google_account_info->id;

    $query = "DELETE FROM pushmsg WHERE touser='$email'";
    $result = mysqli_query($con, $query);
}
catch(Exception $e){
    echo "Unauthorized";
    exit;
}
?>