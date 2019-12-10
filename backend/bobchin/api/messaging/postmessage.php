<?

function PostMessage($title,$body,$ToUser,$meetid,$dbcon){
    $_datetime = date("Y-m-d H:i:s", time());
    $query = "SELECT devicetoken FROM user WHERE email='$ToUser'";
    $result = mysqli_query($dbcon,$query);
    $ToDeviceToken = mysqli_fetch_assoc($result)['devicetoken'];

    $query = "INSERT INTO pushmsg VALUES('','$title','$body','$ToUser','$meetid','$_datetime')";
    $result = mysqli_query($dbcon,$query);

    $Notifyjson=array();
    $Notifyjson['notification']=['title'=>$title, 'body'=>$body]; //메시지 제목, 내용
    $Notifyjson['to']=$ToDeviceToken; // 디바이스 Token
    $Notifyjson['priority']="high";
    $Notifyjson['data']=['title'=>$title, 'message'=>$body];
    $Notifyjson = json_encode($Notifyjson);

    $ServerKey= "key=AAAAQOzQGT4:APA91bEwYz1D-L4cTBWkIo70ISJXCqacMuxWApULxpWWWTqb72CbtbgZ3sSsGWhuuEYbCZwOKh36ioRG-_ILC70-fQyvVacj26LE_aUR8WAUF3CZZa7Y0aD3Q30L-tj-uuB9iteta9Wb";
    $CURL_HEADER=array('Accept: application/json', 'Content-Type: application/json','Authorization: '.$ServerKey);
    
    $ch = curl_init();
    curl_setopt($ch,CURLOPT_URL,"https://fcm.googleapis.com/fcm/send");
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, false);    //요청 결과를 문자열로 반환 
    curl_setopt($ch, CURLOPT_HTTPHEADER, $CURL_HEADER);
    curl_setopt($ch, CURLOPT_CONNECTTIMEOUT, 10);      //connection timeout 10초 
    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);   //원격 서버의 인증서가 유효한지 검사 안함
    curl_setopt($ch, CURLOPT_POSTFIELDS, $Notifyjson);       //POST data
    curl_setopt($ch, CURLOPT_WRITEFUNCTION, 'do_nothing');
    curl_setopt($ch, CURLOPT_POST, true);  
    curl_exec($ch);
    curl_close($ch);
}

function do_nothing($curl, $input) {
    return 0;
}
?>