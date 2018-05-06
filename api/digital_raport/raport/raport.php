<?php
  require_once('../config.php');
  // Koneksi library FPDF
  require('../library/fpdf/fpdf.php');

  header("Content-Type:application/json");
  //$data = json_decode(file_get_contents('php://input'), true);

  if($_SERVER['REQUEST_METHOD']=='GET'){

    $nis = $_GET['nis'];
    $semester = $_GET['semester'];

    $sql = "SELECT
                raport.id,raport.tahun_ajaran AS tahun,
                semester.semester,
                user.nis,user.nama, user.kelas, user.photo,
                mapel.kodeMapel, mapel.namaMapel,
                penilaian.uts, penilaian.uas, penilaian.tugas
            FROM penilaian
            JOIN raport ON raport.id = penilaian.id_raport
            JOIN user ON user.id = raport.id_siswa
            JOIN semester ON semester.id = raport.id_semester
            JOIN mapel ON mapel.id = penilaian.id_mapel
            WHERE nis = '$nis' AND semester = '$semester' "
            ;

    $res = mysqli_query($con, $sql);
    $check = mysqli_fetch_array(mysqli_query($con,$sql));
    if(isset($check)){
      // Setting halaman PDF
      $pdf = new FPDF('p','mm','A4');
      // Menambah halaman baru
      $pdf->AddPage();
      // Setting jenis font
      $pdf->SetFont('Arial','B',16);
      // Membuat string
      $pdf->Cell(190,7,'RAPORT SMKN 1 JAKARTA',0,1,'C');
      $pdf->SetFont('Arial','B',9);
      $pdf->Cell(190,7,'Jl. Abece No. 80 Kodamar, jakarta Utara.',0,1,'C');
      // Setting spasi kebawah supaya tidak rapat
      $pdf->Cell(10,7,'',0,1);

      $data = mysqli_fetch_array(mysqli_query($con, $sql));
      $pdf->SetFont('Arial','',10);
      // $url = 'http://'.$_SERVER['SERVER_NAME'].$data['photo'];
      // $pdf->Image($url,150,30,30,40);
      $pdf->Cell(30,7,'NIS',2,0);
      $pdf->Cell(5,7,':',2,0);
      $pdf->Cell(30,7,$data['nis'],2,1);
      $pdf->Cell(30,7,'NAMA',2,0);
      $pdf->Cell(5,7,':',2,0);
      $pdf->Cell(30,7,$data['nama'],2,1);
      $pdf->Cell(30,7,'KELAS',2,0);
      $pdf->Cell(5,7,':',2,0);
      $pdf->Cell(30,7,$data['kelas'],2,1);
      $pdf->Cell(30,7,'SEMESTER',2,0);
      $pdf->Cell(5,7,':',2,0);
      $pdf->Cell(30,7,$data['semester'],2,1);
      $pdf->Cell(30,7,'TAHUN AJARAN',2,0);
      $pdf->Cell(5,7,':',2,0);
      $pdf->Cell(30,7,$data['tahun'],2,1);

      $pdf->Cell(30,7,'',2,1);
      $pdf->Cell(30,7,'',2,1);

      $pdf->SetFont('Arial','B',10);
      $pdf->Cell(10,6,'NO',1,0,'C');
      $pdf->Cell(40,6,'KODE',1,0,'C');
      $pdf->Cell(80,6,'MATA PELAJARAN',1,0,'C');
      $pdf->Cell(20,6,'TUGAS',1,0,'C');
      $pdf->Cell(20,6,'UTS',1,0,'C');
      $pdf->Cell(20,6,'UAS',1,1,'C');

      $pdf->SetFont('Arial','',10);
      $query = mysqli_query($con, $sql);
      $nomor = 1;
      while ($row = mysqli_fetch_array($query)){
          $pdf->Cell(10,6,$nomor,1,0,'C');
          $pdf->Cell(40,6,$row['kodeMapel'],1,0);
          $pdf->Cell(80,6,$row['namaMapel'],1,0);
          $pdf->Cell(20,6,$row['tugas'],1,0);
          $pdf->Cell(20,6,$row['uts'],1,0);
          $pdf->Cell(20,6,$row['uas'],1,1);
          $nomor = $nomor + 1;
      }

      $path = '../uploads/';
      $fileName = 'raport_'.$nis.'_semester'.$data['semester'].'.pdf';

      $pdf->Output($fileName,'F');

      $response["message"] = 1;
      $response["result"] = "digital_raport/uploads/".$fileName;
      echo json_encode($response);

    } else {
      $response["message"] = 0;
      $response["result"] = "Anda belum mengikuti semester ini!";
      echo json_encode($response);
    }


  }

 ?>
