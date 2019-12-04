<?
include("auth/rcvcode.php");
?>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="css/global.css">
    </head>
    <body class="bg-light row h-100 px-3">
        <div class="vertical-nav bg-white col-2 h-100" id="sidebar">
            <p class="text-gray font-weight-bold text-uppercase px-3 pb-4 mb-0 pt-3">밥친 관리자 페이지</p>
            <ul class="nav flex-column bg-white mb-0">
              <li class="nav-item active">
                <a href="index.php" class="nav-link text-dark bg-light">
                          <i class="fa fa-handshake mr-3 text-primary fa-fw"></i>
                          밥친
                      </a>
              </li>
              <li class="nav-item">
                <a href="users.php" class="nav-link text-dark bg-light">
                          <i class="fa fa-users mr-3 text-primary fa-fw"></i>
                          유저
                      </a>
              </li>
              <li class="nav-item">
                <a href="notice.php" class="nav-link text-dark bg-light">
                          <i class="fa fa-exclamation-circle mr-3 text-primary fa-fw"></i>
                          공지
                      </a>
              </li>
              <li class="nav-item">
                <a href="images.php" class="nav-link text-dark bg-light">
                          <i class="fa fa-images mr-3 text-primary fa-fw"></i>
                          이미지
                      </a>
              </li>
              <li class="nav-item">
                <a href="logout.php" class="nav-link text-dark bg-light">
                          <i class="fa fa-power-off mr-3 text-primary fa-fw"></i>
                          로그아웃
                </a>
              </li>
            </ul>
        </div>
        <div class="content col-10">
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">밥짱</th>
                        <th scope="col">유저</th>
                        <th scope="col">이름</th>
                        <th scope="col">메시지</th>
                        <th scope="col">연령대</th>
                        <th scope="col">위치</th>
                        <th scope="col">시간</th>
                        <th scope="col">인원</th>
                        <th scope="col">옵션</th>
                    </tr>
                </thead>
                <tbody>
                    <?
                      $query="SELECT * FROM meetings";
                      $result = mysqli_query($con,$query);
                      while($row=mysqli_fetch_assoc($result)){
                    ?>
                    <tr>
                        <th scope="row"><?=$row['meetID']?></th>
                        <td><?=$row['headuser']?></td>
                        <td><a data-toggle="collapse" href="#collapse<?=$row['meetID']?>">보기</a></td>
                        <td><?=$row['meetname']?></td>
                        <td><?=$row['meetmsg']?></td>
                        <td><?=$row['agemin']?>~<?=$row['agemax']?></td>
                        <td><?=$row['location']?></td>
                        <td><?=$row['starttime']?>, <?=$row['duration']?>분</td>
                        <td><?=$row['maxpeople']?></td>
                        <td>
                            <a href="action/deletemeet.php?id=<?=$row['meetID']?>" class="btn btn-outline-danger"><i class="fas fa-minus-circle"></i></a>
                        </td>
                    </tr>
                    <div class="collapse" id="collapse<?=$row['meetID']?>">
                        <a class="btn btn-outline-danger float-right" data-toggle="collapse" href="#collapse<?=$row['meetID']?>">X</a>
                        <div class="card card-body">
                            <?=$row['users']?>
                        </div>
                    </div>
                    <?
                        }
                    ?>
                </tbody>
            </table>
        </div>
    </body>
</html>