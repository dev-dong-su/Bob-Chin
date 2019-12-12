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
              <li class="nav-item">
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
              <li class="nav-item active">
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
            <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">공지 추가</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <form id="addimage" action="../img/uploadimg.php?for=notice" method="post" enctype="multipart/form-data">
                                <div class="form-group">
                                    <label for="imagefile" class="col-form-label">이미지</label>
                                    <input type="file" class="form-control" id="imagefile" name="imagefile">
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" onclick="$('#addimage').submit();">추가</button>
                        </div>
                    </div>
                </div>
            </div>
            <a class="btn btn-success" data-toggle="modal" data-target="#exampleModal"><i class="fas fa-plus-circle text-white"></i></a>
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">URL</th>
                        <th scope="col">생성시각</th>
                        <th scope="col">이미지</th>
                        <th scope="col">옵션</th>
                    </tr>
                </thead>
                <tbody>
                    <?
                      $query="SELECT * FROM notice ORDER BY datetime DESC";
                      $result = mysqli_query($con,$query);
                      while($row=mysqli_fetch_assoc($result)){
                    ?>
                    <tr>
                        <th scope="row"><?=$row['notinum']?></th>
                        <td><?=$row['url']?></td>
                        <td><?=$row['datetime']?></td>
                        <td><a data-toggle="collapse" href="#collapse<?=$row['notinum']?>">보기</a></td>
                        <td>
                            <a href="action/deletenotice.php?id=<?=$row['notinum']?>" class="btn btn-outline-danger"><i class="fas fa-minus-circle"></i></a>
                        </td>
                    </tr>
                    <div class="collapse" id="collapse<?=$row['notinum']?>">
                        <a class="btn btn-outline-danger float-right" data-toggle="collapse" href="#collapse<?=$row['notinum']?>">X</a>
                        <div class="card card-body">
                            <img src="<?=$row['url']?>">
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