<?
include_once('../auth/settings.php');

$_url = $_POST['url'];
if(!$last_accesstoken) {echo "Unauthorized"; exit;}
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

    mysqli_query($con, "UPDATE user SET photo='$_url' WHERE email='$email'");
}
catch(Exception $e){
    echo "Unauthorized";
    exit;
}

?>