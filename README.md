# Note AI – iOS Mobil Otomasyon

Java + Appium (XCUITest driver) + TestNG ile yazılmış, gerçek iPhone üzerinde
çalışacak uçtan uca test paketi. Senaryo `NoteAIFlowTest` içinde adım adım
(step1 → step10) uygulanmış durumda: Get Started ekranı, Terms of Use /
Privacy Policy doğrulaması, onboarding, paywall'da 3 günlük deneme
başlatma, Home ekranı, sesli not oluşturma, not detayına girme, çeviri ve
(opsiyonel) quiz üretimi.

## Bu ortamda şu ana kadar kurulanlar

- Node.js, npm (Homebrew)
- Maven (Homebrew)
- Appium server + XCUITest driver (`npm i -g appium`, `appium driver install xcuitest`)
- libimobiledevice, ideviceinstaller, ios-deploy (gerçek cihazla konuşmak için)
- Maven proje iskeleti: `pom.xml`, Page Object Model (`src/test/java/.../pages`),
  `BaseTest`/`BasePage`, `ConfigReader`, `NoteAIFlowTest`, `testng.xml`
- `mvn test-compile` başarıyla derleniyor (Appium 9.3.0 ↔ Selenium 4.24.0
  uyumluluğu için Selenium sürümü pinlendi — bkz. `pom.xml` yorum satırı)

## Senin tamamlaman gereken adımlar

### 1. Tam Xcode kurulumu
Şu an sadece Command Line Tools var. App Store'dan **Xcode**'u indir, kur,
sonra:
```
sudo xcode-select -s /Applications/Xcode.app/Contents/Developer
sudo xcodebuild -license accept
xcodebuild -runFirstLaunch
```

### 2. Apple Developer hesabı (ücretsiz "Personal Team" yeterli)
Xcode → Settings → Accounts → Apple ID'ni ekle → ilgili Team'i seç →
**Team ID**'yi not al (`src/test/resources/config.properties` içindeki
`wda.xcodeOrgId` alanına yazılacak). WebDriverAgent bu sertifika ile
imzalanıp cihaza kurulacak.

### 3. iPhone'u hazırla
- iPhone'u Mac'e USB ile bağla, "Bu bilgisayara güven" iste.
- iOS 16+ ise **Ayarlar → Gizlilik ve Güvenlik → Developer Mode**'u aç, cihazı
  yeniden başlat ve onayla.
- İlk WebDriverAgent kurulumunda "Untrusted Developer" uyarısı çıkarsa
  **Ayarlar → Genel → VPN ve Cihaz Yönetimi**'nden sertifikaya güven.
- UDID'yi öğren:
  ```
  xcrun xctrace list devices
  # veya
  idevice_id -l
  ```

### 4. `src/test/resources/config.properties` içini doldur
`CHANGE_ME_*` ile işaretli tüm alanlar (device.udid, device.name,
platform.version, app.bundleId, wda.xcodeOrgId) gerçek değerlerle
değiştirilmeli. `app.bundleId`'yi bulmak için uygulamayı cihaza kurduktan
sonra:
```
ideviceinstaller -l | grep -i note
```

### 5. Gerçek element locator'larını bul (en kritik adım)
Şu an tüm Page Object'lerdeki `accessibilityId(...)` değerleri **placeholder
(TODO)**. Gerçek uygulamanın UI'ı incelenmeden bu ID'ler bilinemez. Appium
Inspector ile (`appium-inspector` GUI uygulaması, veya `npm i -g
appium-inspector` yerine masaüstü indirilebilir sürüm) cihaza bağlanıp her
ekrandaki gerçek `accessibility identifier` / `name` değerlerini alıp
ilgili `pages/*.java` dosyalarındaki `By` sabitlerini güncellemen gerekiyor.
Bu depoda yer alan sınıflar: `GetStartedPage`, `TermsOfUsePage`,
`PrivacyPolicyPage`, `OnboardingPage`, `PaywallPage`, `HomePage`,
`AudioUploadPage`, `NoteDetailPage`, `NoteToolsPage`.

### 6. Test ses dosyası
`AudioUploadPage`, cihazdaki Dosyalar uygulamasından bir ses dosyası
seçiyor. Cihazda (On My iPhone / iCloud Drive) `test-audio.m4a` adında bir
dosya bulunmalı, ya da `config.properties`'teki `audio.testFileName`
değerini elindeki dosyanın adıyla değiştir.

## Bilinen sınırlamalar

- **Paywall / 3 Günlük Deneme**: Gerçek cihazda StoreKit satın alma akışı
  Face ID/Touch ID/şifre onayı istiyor; bunu Appium otomatikleştiremez. Test
  cihazında bir **Sandbox Apple ID** ile oturum açıp ilk çalıştırmada onayı
  manuel geçmen gerekebilir.
- **App Store build'i Simulator'da çalışmaz** (mimari uyumsuzluğu) — bu
  yüzden hedef gerçek cihaz olarak seçildi.

## Çalıştırma

```
# 1) Appium server'ı başlat (ayrı bir terminalde)
appium

# 2) Testleri çalıştır
mvn test
```

Sadece zorunlu (quiz hariç) adımları çalıştırmak istersen, `testng.xml`'e
`<exclude name="optional"/>` ekleyebilirsin.
