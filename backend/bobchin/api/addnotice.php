<?
include_once('../auth/settings.php');

$_msg = $_POST['msg'];
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
    
    $query = "SELECT auth_level FROM user WHERE email='$email'";
    $result = mysqli_query($con, $query);
    if(mysqli_fetch_assoc($result)['auth_level'] >= 2){
        $query = "INSERT INTO notice VALUES('','$_msg','$_datetime')";
        mysqli_query($con, $query);
    }
}
catch(Exception $e){
    echo "Unauthorized";
    exit;
}
    
?>