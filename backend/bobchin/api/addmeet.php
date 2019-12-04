<?
include_once('../auth/settings.php');
$_meetname = $_POST['meetname'];
$_meetmsg = $_POST['meetmsg'];
$_agemax = $_POST['agemax'];
$_agemin = $_POST['agemin'];
$_location = $_POST['location'];
$_starttime = $_POST['starttime'];
$_duration = $_POST['duration'];
$_maxpeople = $_POST['maxpeople'];

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
    
    echo "wdwasgdhf";
    
    $query = "INSERT INTO meetings VALUES('','$email','|$email|','$_meetname','$_meetmsg','$_agemax','$_agemin','$_location','$_starttime','$_duration','$_maxpeople','')";
    $result = mysqli_query($con, $query);
    
    //no response
}
catch(Exception $e){
    echo "Unauthorized";
    exit;
}
?>