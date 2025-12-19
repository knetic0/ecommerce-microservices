package com.mehmetsolak.emailservice.templates;

public final class WelcomeEmailTemplate {

    private WelcomeEmailTemplate() {}

    public static final String BODY = """
    <!DOCTYPE html>
    <html lang="tr">
    <head>
        <meta charset="UTF-8">
        <title>Hoş Geldin</title>
    </head>
    <body style="margin:0; padding:0; background-color:#f4f6f8; font-family:Arial, Helvetica, sans-serif;">
    <table width="100%" cellpadding="0" cellspacing="0" style="background-color:#f4f6f8; padding:40px 0;">
        <tr>
            <td align="center">
                <table width="600" cellpadding="0" cellspacing="0" style="background-color:#ffffff; border-radius:8px; overflow:hidden;">
                   \s
                    <!-- Header -->
                    <tr>
                        <td style="background-color:#4f46e5; padding:24px; text-align:center;">
                            <h1 style="color:#ffffff; margin:0; font-size:24px;">
                                Hoş Geldin 👋
                            </h1>
                        </td>
                    </tr>
   \s
                    <!-- Body -->
                    <tr>
                        <td style="padding:32px; color:#333333;">
                            <p style="font-size:16px; margin:0 0 16px;">
                                Merhaba <strong>{{fullName}}</strong>,
                            </p>
   \s
                            <p style="font-size:15px; line-height:1.6; margin:0 0 24px;">
                                Hesabın başarıyla oluşturuldu 🎉 \s
                            </p>
                        </td>
                    </tr>
   \s
                    <!-- Footer -->
                    <tr>
                        <td style="background-color:#f9fafb; padding:20px; text-align:center; font-size:12px; color:#999999;">
                            © 2025 Your Company Name \s
                            <br/>
                            Bu email otomatik olarak gönderilmiştir.
                        </td>
                    </tr>
   \s
                </table>
            </td>
        </tr>
    </table>
    </body>
    </html>
   \s""";
}
