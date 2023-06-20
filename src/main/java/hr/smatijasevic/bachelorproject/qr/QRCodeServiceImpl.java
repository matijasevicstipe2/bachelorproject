package hr.smatijasevic.bachelorproject.qr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import hr.smatijasevic.bachelorproject.security.user.Account;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class QRCodeServiceImpl implements QRCodeService {

    private QRCodeRepository qrCodeRepository;

    public QRCodeServiceImpl(QRCodeRepository qrCodeRepository) {
        this.qrCodeRepository = qrCodeRepository;
    }

    @Override
    public QRCode saveQRCode(QRCode qrCode) {
        return qrCodeRepository.save(qrCode);
    }

    @Override
    public QRCode getQRCodeById(Long id) {
        return qrCodeRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteQRCode(Long id) {
        qrCodeRepository.deleteById(id);
    }

    @Override
    public BitMatrix generateQRcode(String data)
            throws WriterException, IOException {
        String charset = "UTF-8";
        Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<>();
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        BitMatrix matrix = new MultiFormatWriter()
                .encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, 200, 200, hashMap);
        return matrix;
    }

    @Override
    public Optional<QRCode> getQRCodeByAccountAndQrPass(Account account, String qrPass) {
        return qrCodeRepository.findByAccountAndQrPass(account, qrPass);
    }

}

