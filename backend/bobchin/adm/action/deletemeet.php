<?
include("../auth/rcvcode.php");

$query = "DELETE FROM meetings WHERE meetID='$_GET[id]'";
mysqli_query($con,$query);
?>
<script>
    window.location="../index.php";
</script>