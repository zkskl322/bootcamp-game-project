import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:dio/dio.dart';
import 'package:game_frontend/Game/lobby.dart';
import 'package:game_frontend/backup/login_page.dart';

void main() {
  runApp(const FigmaToCodeApp());
}

// Generated by: https://www.figma.com/community/plugin/842128343887142055/
class FigmaToCodeApp extends StatelessWidget {
  const FigmaToCodeApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      theme: ThemeData.dark().copyWith(
        scaffoldBackgroundColor: const Color.fromARGB(255, 18, 32, 47),
      ),
      home: Scaffold(
        body: ListView(children: [
          LoginPage(),
        ]),
      ),
    );
  }
}

class LoginPage extends StatefulWidget {
  @override
  _LoginPageState createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  final TextEditingController _EmailController = TextEditingController();
  final TextEditingController _PasswordController = TextEditingController();


  Future<void> _handleLoginButton() async {
    final dio = Dio();

    try {
      final Response response =
        await dio.post(
          'http://localhost:8080/login', 
          data: {
            'username': _EmailController.text,
            'password': _PasswordController.text,
          }
      );
      if (response.statusCode == 200) {
        print(response);
      } else {
        print(response);
      }
    } catch (e) {
      print(e);
    }
  }
  @override
  void initState() {
    super.initState();
    print("hello");
    print("hello");
    print("hello");
    print("hello");
    print("hello");
    print("hello");
    print("hello");
    print("hello");
    print("hello");
    print("hello");
    print("hello");
    print("hello");
    print("hello");
    print("hello");
    print("hello");
    print("hello");
    print("hello");
    print("hello");
    print("hello");
    print("hello");
    print("hello");
    print("hello");
    print("hello");
    print("hello");
    print("hello");
    print("hello");
    print("hello");
    print("hello");
    print("hello");
    print("hello");
  }

  @override
  Widget build(BuildContext context) {
    final Size screenSize = MediaQuery.of(context).size;
    return Column(
      children: [
        ElevatedButton(
          onPressed: () {
            Navigator.push(
              context,
              MaterialPageRoute(builder: (context) => LobbyPage()),
            );
          },
          child: const Text('Go to Login Page'),
        ),
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
                // soccer game text
                TextSpan(
                  children: [
                    TextSpan(
                      text: 'Soccer Game',
                      style: TextStyle(
                        color: Color(0xFF07021F),
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
                                  'Email',
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
                                    controller: _EmailController,
                                    decoration: const InputDecoration(
                                      hintText: 'example@exam.com',
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
              Row(
                //social login btn
                mainAxisSize: MainAxisSize.min,
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: [
                  Container(
                    width: 522,
                    height: 60,
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
                          width: 23.45,
                          height: 24,
                          clipBehavior: Clip.antiAlias,
                          decoration: const BoxDecoration(),
                          child: const FlutterLogo(),
                        ),
                        const SizedBox(width: 12),
                        const Text(
                          'Sign in with google',
                          style: TextStyle(
                            color: Color(0xFF7D7D7D),
                            fontSize: 24,
                            fontFamily: 'Inter',
                            fontWeight: FontWeight.w500,
                            height: 0.04,
                          ),
                        ),
                      ],
                    ),
                  ),
                ],
              ),
              const SizedBox(height: 30),
              const Row(
                //sign up text
                mainAxisSize: MainAxisSize.min,
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: [
                  Text(
                    'Are you new?',
                    style: TextStyle(
                      color: Color(0xFF7D7D7D),
                      fontSize: 20,
                      fontFamily: 'Inter',
                      fontWeight: FontWeight.w500,
                      height: 0.03,
                    ),
                  ),
                  SizedBox(width: 4),
                  Text(
                    'Create an account',
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
            ],
          ),
        ),
      ],
    );
  }
}





                  // Positioned(
                  //   left: 1106.77,
                  //   top: -39,
                  //   child: Transform(
                  //     transform: Matrix4.identity()
                  //       ..translate(0.0, 0.0)
                  //       ..rotateZ(0.26),
                  //     child: Container(
                  //       width: 751.85,
                  //       height: 1019.13,
                  //       decoration: ShapeDecoration(
                  //         image: const DecorationImage(
                  //           image: NetworkImage(
                  //               "https://via.placeholder.com/752x1019"),
                  //           fit: BoxFit.fill,
                  //         ),
                  //         shape: RoundedRectangleBorder(
                  //           borderRadius: BorderRadius.circular(30),
                  //         ),
                  //       ),
                  //     ),
                  //   ),
                  // ),