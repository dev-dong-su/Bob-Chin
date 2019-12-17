<?
// include "../../auth/dbconnect.php";
// PostMessage("test","test","gorae02@gmail.com","17",$con);

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

    curl_request_async("https://fcm.googleapis.com/fcm/send",$Notifyjson);
}

function curl_for_map($long,$lat){
    $ServerKey = "8c28b1af89ae1c8cd422aedb8699f181";
    $CURL_HEADER=array('Accept: application/json', 'Content-Type: application/json','Authorization: KakaoAK '.$ServerKey);
    $ch = curl_init();
    curl_setopt($ch,CURLOPT_URL,"https://dapi.kakao.com/v2/local/geo/coord2regioncode.json?x=$long&y=$lat");
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);    //요청 결과 리턴
    curl_setopt($ch, CURLOPT_HTTPHEADER, $CURL_HEADER);
    curl_setopt($ch, CURLOPT_CONNECTTIMEOUT, 10);      //connection timeout 10초 
    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);   //원격 서버의 인증서가 유효한지 검사 안함
    $response = curl_exec($ch);
    curl_close($ch);

    return $response;
}

function curl_request_async($url, $post_string)  
{  
    $parts=parse_url($url);  
    $fp = fsockopen("ssl://" . $parts['host'], 443, $errno, $errstr, 30);  
    
    $ServerKey= "key=AAAAQOzQGT4:APA91bEwYz1D-L4cTBWkIo70ISJXCqacMuxWApULxpWWWTqb72CbtbgZ3sSsGWhuuEYbCZwOKh36ioRG-_ILC70-fQyvVacj26LE_aUR8WAUF3CZZa7Y0aD3Q30L-tj-uuB9iteta9Wb";

    $out = "POST ".$parts['path']." HTTP/1.1\r\n";  
    $out.= "Host: ".$parts['host']."\r\n";
    $out.= "Authorization: ".$ServerKey."\r\n";
    $out.= "Content-Type: application/json\r\n";  
    $out.= "Content-Length: ".strlen($post_string)."\r\n";  
    $out.= "Connection: Close\r\n\r\n";  
    $out.= $post_string;  
  
    fwrite($fp, $out);
    fclose($fp);  
}  
?>