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
$_photo = $_POST['photo'];

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
    
    $long = explode(", ",$_location)[0];
    $lat = explode(", ",$_location)[1];
    $KAKAOMAP_JSON = curl_for_map($long,$lat);
    $KAKAOMAP_ARR = json_decode($KAKAOMAP_JSON);
    $_region = $KAKAOMAP_ARR->documents[0]->region_2depth_name." ".$KAKAOMAP_ARR->documents[0]->region_3depth_name;


    $query = "INSERT INTO meetings VALUES('','$email','|$email|','$_meetname','$_meetmsg','$_agemax','$_agemin','$_location','$_region','$_starttime','$_duration','$_maxpeople','$_photo')";
    $result = mysqli_query($con, $query);
    
    //no response
}
catch(Exception $e){
    echo "Unauthorized";
    exit;
}
?>