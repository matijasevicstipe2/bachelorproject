package hr.smatijasevic.bachelorproject.qr;

import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import hr.smatijasevic.bachelorproject.security.user.Account;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public interface QRCodeService {

    QRCode saveQRCode(QRCode qrCode);

    QRCode getQRCodeById(Long id);

    void deleteQRCode(Long id);

    BitMatrix generateQRcode(String data)
            throws WriterException, IOException;

    Optional<QRCode> getQRCodeByAccountAndQrPass(Account account, String qrPass);

}

