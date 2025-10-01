#!/data/data/com.termux/files/usr/bin/bash
set -e

# Cài gói cần thiết
pkg install wget unzip openjdk-17 -y

# Biến môi trường
export JAVA_HOME=$PREFIX/opt/openjdk
export PATH=$JAVA_HOME/bin:$PATH

# Tải và cài build-tools 33.0.2 (có aapt2)
mkdir -p $HOME/android-sdk/build-tools/33.0.2
cd $HOME
wget https://dl.google.com/android/repository/build-tools_r33.0.2-linux.zip -O build-tools.zip
unzip -o build-tools.zip -d android-sdk/build-tools/33.0.2
rm build-tools.zip

# Thêm PATH
echo 'export PATH=$HOME/android-sdk/build-tools/33.0.2:$PATH' >> ~/.bashrc
source ~/.bashrc

# Xoá cache gradle cũ
rm -rf $HOME/.gradle/caches

# Quay lại project và build
cd $HOME/NoRestartModule
./gradlew clean assembleDebug

# Copy APK ra Download
cp app/build/outputs/apk/debug/app-debug.apk /sdcard/Download/
echo "✅ Done! APK đã ở /sdcard/Download/app-debug.apk"
