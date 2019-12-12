<?
include_once('../auth/settings.php');

$_meetid = $_POST['meetid'];

$google_oauth = new Google_Service_Oauth2($client);
try{
    $google_account_info = $google_oauth->userinfo->get();
    $email = $google_account_info->email;
    $name = $google_account_info->name;
    $id = $google_account_info->id;

    $query = "SELECT users, maxpeople,headuser,agemin,agemax FROM meetings WHERE meetID='$_meetid'";
    $result = mysqli_query($con, $query);
    $row = mysqli_fetch_array($result);

    $headuser= $row['headuser'];
    $users = $row['users'];
    $maxpeople = $row['maxpeople'];
    $usercnt = substr_count($users,'|')-1;
    $is_existinguser = substr_count($users, $email);

    if( !($usercnt < $maxpeople) ){
        echo 1; exit;
    }
    if($is_existinguser){
        echo 2; exit;
    }
    {
        PostMessage('밥친 알림','새로운 밥친 '.$name.'이 참가했습니다!',$headuser,$_meetid,$con);
        $users = $users."$email|";
        $query = "UPDATE meetings SET users='$users' WHERE meetID='$_meetid'";
        mysqli_query($con, $query);
        echo 0;
    }

    /*
    에러코드
    1 - 유저 꽉참
    2 - 이미 모임에 참가중
    */
}
catch(Exception $e){
    echo "Unauthorized";
    exit;
}

?>