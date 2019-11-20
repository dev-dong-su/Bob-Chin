<?
include_once('../auth/settings.php');

$_meetid = $_POST['meetid'];
$_message = $_POST['message'];
$_datetime = date("Y-m-d H:i:s", time());

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
    
    $query = "INSERT INTO chat VALUES('','$_meetid','$email','$_message','$_datetime')";
    $result = mysqli_query($con, $query);
}
catch(Exception $e){
    echo "Unauthorized";
    exit;
}
    
?>