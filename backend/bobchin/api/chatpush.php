<?
include_once('../auth/settings.php');

$_meetid=$_POST['meetid'];

$google_oauth = new Google_Service_Oauth2($client);
try{
    $google_account_info = $google_oauth->userinfo->get();
    $email = $google_account_info->email;
    $name = $google_account_info->name;
    $id = $google_account_info->id;

    $query= "SELECT users FROM meetings WHERE meetID='$_meetid'";
    $result = mysqli_query($con,$query); 
    $row = mysqli_fetch_row($result);
    $users = explode('|',(string)$row[0]);
    for($i=1;$i<count($users)-1;$i++){
        if ($users[$i] == $email) continue;
        PostMessage('밥친 알림', '새 채팅이 도착했습니다!', $users[$i], $_meetid, $con);
    }
}
catch(Exception $e){
    echo "Unauthorized";
    exit;
}
?>