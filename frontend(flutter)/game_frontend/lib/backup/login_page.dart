import 'dart:convert';
import 'dart:io';
import 'dart:js' as js;
import 'dart:html';

import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:dio/dio.dart';
import 'package:game_frontend/backup/game_lobby.dart';
import 'package:game_frontend/backup/signup_page.dart';
import 'package:http/http.dart' as http;

import "package:firebase_auth/firebase_auth.dart";
import 'package:firebase_core/firebase_core.dart';
import 'package:game_frontend/firebase_options.dart';

import 'package:google_sign_in/google_sign_in.dart';
import 'package:kakao_flutter_sdk_user/kakao_flutter_sdk_user.dart'
    as kakaoUser;
import 'package:shared_preferences/shared_preferences.dart';

// Generated by: https://www.figma.com/community/plugin/842128343887142055/
class Login_Page extends StatelessWidget {
  const Login_Page({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      theme: ThemeData.dark().copyWith(
        scaffoldBackgroundColor: const Color.fromARGB(255, 18, 32, 47),
      ),
      home: Scaffold(
        body: ListView(children: [
          Login(),
        ]),
      ),
    );
  }
}

class Login extends StatefulWidget {
  @override
  _LoginPageState createState() => _LoginPageState();
}

class _LoginPageState extends State<Login> {
  final TextEditingController _NicknameController = TextEditingController();
  final TextEditingController _PasswordController = TextEditingController();
  final FirebaseAuth _auth = FirebaseAuth.instance;
  final GoogleSignIn _googleSignIn = GoogleSignIn();
  final Storage _storage = window.localStorage;
  late final FirebaseApp app;
  late final FirebaseAuth auth;

  Future<void> _handleLoginButton() async {
    final dio = Dio();

    try {
      final Response response = await dio.post(
        'http://192.168.3.3:8080/login',
        data: {
          'username': _NicknameController.text,
          'password': _PasswordController.text,
        },
      );
      if (response.statusCode == 200) {
        Navigator.push(
            context, MaterialPageRoute(builder: (context) => Game_Lobby()));
        _storage['token'] = response.data['token'];
      } else {
        print("Login failed: ${response.statusCode}");
      }
    } catch (e) {
      print("Error: $e");
    }
  }

  Future<void> _handleSocialLoginButton_G(BuildContext context) async {
    // Dio 인스턴스 생성 - HTTP 요청을 처리하기 위한 라이브러리
    Dio dio = Dio();

    try {
      // 구글 로그인 처리 -> 화면에 구글 로그인 페이지가 표시됨
      final GoogleSignInAccount? googleUser = await _googleSignIn.signIn();

      if (googleUser == null) {
        // 사용자가 로그인을 취소한 경우 처리
        print('cancel google login');
        return;
      }

      // 구글 로그인 인증 정보 가져오기
      final GoogleSignInAuthentication googleAuth =
          await googleUser.authentication;

      // 구글 OAuth 인증 정보 생성
      final OAuthCredential credential = GoogleAuthProvider.credential(
        accessToken: googleAuth.accessToken,
        idToken: googleAuth.idToken,
      );

      // Firebase로 구글 인증 정보로 로그인
      final UserCredential userCredential =
          await _auth.signInWithCredential(credential);

      // 현재 Firebase 인증 인스턴스와 로그인된 사용자 가져오기
      FirebaseAuth auth = FirebaseAuth.instance;
      User? currentUser = auth.currentUser;

      if (currentUser != null) {
        // 현재 사용자의 ID 토큰을 가져오기
        String? idToken = await currentUser.getIdToken(true);

        // 서버에 로그인 요청 보내기
        final Response response = await dio.post(
          'http://192.168.3.3:8080/login',
          options: Options(
            headers: {
              'Authorization': 'Bearer $idToken', // 헤더에 인증 토큰 추가
              'Social': 'Google', // 헤더에 소셜 로그인 정보 추가
            },
          ),
        );

        // 서버 응답이 성공적일 경우
        if (response.statusCode == 200) {
          // 게임 로비 페이지로 이동
          Navigator.push(
              context, MaterialPageRoute(builder: (context) => Game_Lobby()));
          // 받은 토큰을 저장소에 저장
          _storage['token'] = response.data['token'];
        } else {
          // 서버 응답이 실패할 경우 디버그 모드에서 오류 메시지 출력
          if (kDebugMode) {
            print("Login failed: ${response.statusCode}");
          }
        }
      } else {
        // 현재 사용자가 없을 경우 로그인 페이지로 이동
        Navigator.push(context,
            MaterialPageRoute(builder: (context) => const Login_Page()));
      }
    } catch (error) {
      // 예외 발생 시 디버그 모드에서 오류 메시지 출력
      if (kDebugMode) {
        print('구글로 로그인 실패 $error');
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    final Size screenSize = MediaQuery.of(context).size;
    return Column(
      children: [
        Container(
          width: screenSize.width,
          height: 960,
          clipBehavior: Clip.antiAlias,
          decoration: const BoxDecoration(color: Colors.white),
          child: Column(
            children: [
              const SizedBox(
                height: 60,
              ),
              const Text.rich(
                //Soccor game section
                TextSpan(
                  children: [
                    TextSpan(
                      text: 'Soccer ',
                      style: TextStyle(
                        color: Color(0xFF07021F),
                        fontSize: 48,
                        fontFamily: 'Literata',
                        fontWeight: FontWeight.w500,
                        height: 0,
                        letterSpacing: -0.96,
                      ),
                    ),
                    TextSpan(
                      text: 'Game',
                      style: TextStyle(
                        color: Color(0xFF6153BD),
                        fontSize: 48,
                        fontFamily: 'Literata',
                        fontWeight: FontWeight.w500,
                        height: 0,
                        letterSpacing: -0.96,
                      ),
                    ),
                  ],
                ),
              ),
              const SizedBox(height: 80),
              SizedBox(
                //email, password input box
                width: 522,
                child: Column(
                  mainAxisSize: MainAxisSize.min,
                  mainAxisAlignment: MainAxisAlignment.start,
                  crossAxisAlignment: CrossAxisAlignment.end,
                  children: [
                    SizedBox(
                      width: 522,
                      height: 228,
                      child: Column(
                        mainAxisSize: MainAxisSize.min,
                        mainAxisAlignment: MainAxisAlignment.start,
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          SizedBox(
                            //email
                            height: 91,
                            child: Column(
                              mainAxisSize: MainAxisSize.min,
                              mainAxisAlignment: MainAxisAlignment.start,
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                const Text(
                                  'Nickname',
                                  style: TextStyle(
                                    color: Color(0xFF7D7D7D),
                                    fontSize: 24,
                                    fontFamily: 'Inter',
                                    fontWeight: FontWeight.w500,
                                    height: 0,
                                  ),
                                ),
                                const SizedBox(height: 6),
                                Container(
                                  width: 522,
                                  padding: const EdgeInsets.symmetric(
                                      horizontal: 24),
                                  decoration: BoxDecoration(
                                    color: Colors.white,
                                    borderRadius: BorderRadius.circular(4),
                                    border: Border.all(
                                      width: 1,
                                      color: Colors.black.withOpacity(0.1),
                                    ),
                                  ),
                                  child: TextField(
                                    controller: _NicknameController,
                                    decoration: const InputDecoration(
                                      hintText: 'Nickname',
                                      hintStyle: TextStyle(
                                        color: Color(0xFFC1C1C1),
                                        fontSize: 20,
                                        fontFamily: 'Inter',
                                        fontWeight: FontWeight.w500,
                                      ),
                                      border: InputBorder.none,
                                    ),
                                    style: const TextStyle(
                                      color: Colors.black,
                                      fontSize: 20,
                                      fontFamily: 'Inter',
                                      fontWeight: FontWeight.w500,
                                    ),
                                    keyboardType: TextInputType.emailAddress,
                                  ),
                                ),
                              ],
                            ),
                          ),
                          const SizedBox(height: 24),
                          SizedBox(
                            //password
                            height: 91,
                            child: Column(
                              mainAxisSize: MainAxisSize.min,
                              mainAxisAlignment: MainAxisAlignment.start,
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                const Text(
                                  'password',
                                  style: TextStyle(
                                    color: Color(0xFF7D7D7D),
                                    fontSize: 24,
                                    fontFamily: 'Inter',
                                    fontWeight: FontWeight.w500,
                                    height: 0,
                                  ),
                                ),
                                const SizedBox(height: 6),
                                Container(
                                  width: 522,
                                  padding: const EdgeInsets.symmetric(
                                      horizontal: 24),
                                  decoration: BoxDecoration(
                                    color: Colors.white,
                                    borderRadius: BorderRadius.circular(4),
                                    border: Border.all(
                                      width: 1,
                                      color: Colors.black.withOpacity(0.1),
                                    ),
                                  ),
                                  child: TextField(
                                    controller: _PasswordController,
                                    decoration: const InputDecoration(
                                      hintText: 'password',
                                      hintStyle: TextStyle(
                                        color: Color(0xFFC1C1C1),
                                        fontSize: 20,
                                        fontFamily: 'Inter',
                                        fontWeight: FontWeight.w500,
                                      ),
                                      border: InputBorder.none,
                                    ),
                                    style: const TextStyle(
                                      color: Colors.black,
                                      fontSize: 20,
                                      fontFamily: 'Inter',
                                      fontWeight: FontWeight.w500,
                                    ),
                                    keyboardType: TextInputType.text,
                                    obscureText:
                                        true, // 여기서 obscureText를 true로 설정합니다.
                                    obscuringCharacter:
                                        '●', // 원하는 크기의 문자로 설정 (예: '●' 또는 '◼')
                                  ),
                                ),
                              ],
                            ),
                          ),
                        ],
                      ),
                    ),
                    const Text(
                      //find password text
                      'Forgot password? ',
                      style: TextStyle(
                        color: Color(0xFF6153BD),
                        fontSize: 20,
                        fontFamily: 'Inter',
                        fontWeight: FontWeight.w500,
                        decoration: TextDecoration.underline,
                        height: 0,
                      ),
                    ),
                  ],
                ),
              ),
              const SizedBox(height: 30),
              Row(
                //login btn
                mainAxisSize: MainAxisSize.min,
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: [
                  InkWell(
                    onTap: _handleLoginButton, // 로그인 동작을 처리하는 함수 호출
                    child: Container(
                      width: 522,
                      height: 60,
                      padding: const EdgeInsets.symmetric(
                          horizontal: 24, vertical: 12),
                      decoration: ShapeDecoration(
                        color: const Color(0xFF120071),
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(4),
                        ),
                      ),
                      child: const Row(
                        mainAxisSize: MainAxisSize.min,
                        mainAxisAlignment: MainAxisAlignment.center,
                        crossAxisAlignment: CrossAxisAlignment.center,
                        children: [
                          Text(
                            'Log in',
                            style: TextStyle(
                              color: Colors.white,
                              fontSize: 24,
                              fontFamily: 'Inter',
                              fontWeight: FontWeight.w500,
                              height: 0.04,
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                ],
              ),
              const SizedBox(height: 24),
              Row(
                // ----------- or ----------- text
                mainAxisSize: MainAxisSize.min,
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: [
                  Container(
                    width: 144,
                    decoration: const ShapeDecoration(
                      shape: RoundedRectangleBorder(
                        side: BorderSide(
                          width: 2,
                          strokeAlign: BorderSide.strokeAlignCenter,
                          color: Color(0x7F7D7D7D),
                        ),
                      ),
                    ),
                  ),
                  const SizedBox(width: 24),
                  Container(
                    padding: const EdgeInsets.only(bottom: 4),
                    child: const Column(
                      mainAxisSize: MainAxisSize.min,
                      mainAxisAlignment: MainAxisAlignment.center,
                      crossAxisAlignment: CrossAxisAlignment.center,
                      children: [
                        Text(
                          'or',
                          style: TextStyle(
                            color: Color(0xFF7D7D7D),
                            fontSize: 16,
                            fontFamily: 'Inter',
                            fontWeight: FontWeight.w500,
                            height: 0.07,
                          ),
                        ),
                      ],
                    ),
                  ),
                  const SizedBox(width: 24),
                  Container(
                    width: 144,
                    decoration: const ShapeDecoration(
                      shape: RoundedRectangleBorder(
                        side: BorderSide(
                          width: 2,
                          strokeAlign: BorderSide.strokeAlignCenter,
                          color: Color(0x7F7D7D7D),
                        ),
                      ),
                    ),
                  ),
                ],
              ),
              const SizedBox(height: 24),
              Column(
                //sociallogin (google)
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  InkWell(
                    onTap: () => _handleSocialLoginButton_G(context),
                    child: Container(
                      width: 522, // 버튼 너비 조정
                      height: 60, // 버튼 높이 조정
                      padding: const EdgeInsets.symmetric(
                          horizontal: 24, vertical: 12),
                      decoration: ShapeDecoration(
                        shape: RoundedRectangleBorder(
                          side: const BorderSide(
                              width: 1, color: Color(0x7F07021F)),
                          borderRadius: BorderRadius.circular(4),
                        ),
                      ),
                      child: Row(
                        mainAxisSize: MainAxisSize.min,
                        mainAxisAlignment: MainAxisAlignment.center,
                        crossAxisAlignment: CrossAxisAlignment.center,
                        children: [
                          Container(
                            width: 24, // 구글 로고 크기 조정
                            height: 24, // 구글 로고 크기 조정
                            clipBehavior: Clip.antiAlias,
                            decoration: const BoxDecoration(
                              shape: BoxShape.circle,
                            ),
                            child: Image.asset('images/Google.png'),
                          ),
                          const SizedBox(width: 12),
                          const Text(
                            'Sign in with Google',
                            style: TextStyle(
                              color: Color(0xFF7D7D7D),
                              fontSize: 18, // 폰트 크기 조정
                              fontFamily: 'Inter',
                              fontWeight: FontWeight.w500,
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                ],
              ),
              const SizedBox(height: 30),
              Row(
                // sign up text
                mainAxisSize: MainAxisSize.min,
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: [
                  const Text(
                    'Are you new?',
                    style: TextStyle(
                      color: Color(0xFF7D7D7D),
                      fontSize: 20,
                      fontFamily: 'Inter',
                      fontWeight: FontWeight.w500,
                      height: 0.03,
                    ),
                  ),
                  const SizedBox(width: 4),
                  InkWell(
                    onTap: () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(
                            builder: (context) => const Signup_Page()),
                      );
                    },
                    child: const Text(
                      'Create an account',
                      style: TextStyle(
                        color: Color(0xFF6153BD),
                        fontSize: 20,
                        fontFamily: 'Inter',
                        fontWeight: FontWeight.w500,
                        decoration: TextDecoration.underline,
                        height: 1.0,
                      ),
                    ),
                  ),
                ],
              ),
            ],
          ),
        ),
      ],
    );
  }
}
