import 'dart:html';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:dio/dio.dart';
import 'package:game_frontend/backup/game_lobby.dart';
import 'package:game_frontend/backup/signup_page.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:google_sign_in/google_sign_in.dart';

class LoginPageTest extends StatelessWidget {
  const LoginPageTest({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      theme: ThemeData.dark().copyWith(
        scaffoldBackgroundColor: const Color.fromARGB(255, 18, 32, 47),
      ),
      home: Scaffold(
        body: ListView(children: const [LoginPage()]),
      ),
    );
  }
}

class LoginPage extends StatefulWidget {
  const LoginPage({Key? key}) : super(key: key);

  @override
  _LoginPageState createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  final TextEditingController _nicknameController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();
  final FirebaseAuth _auth = FirebaseAuth.instance;
  final GoogleSignIn _googleSignIn = GoogleSignIn();
  final Storage _storage = window.localStorage;

  static const TextStyle _labelStyle = TextStyle(
    color: Color(0xFF7D7D7D),
    fontSize: 24,
    fontFamily: 'Inter',
    fontWeight: FontWeight.w500,
  );

  Future<void> _handleLoginButton() async {
    final dio = Dio();
    try {
      final response = await dio.post(
        'http://localhost:8080/login',
        data: {
          'username': _nicknameController.text,
          'password': _passwordController.text,
        },
      );
      if (response.statusCode == 200) {
        Navigator.pushReplacement(
          context,
          MaterialPageRoute(builder: (context) => Game_Lobby()),
        );
        _storage['token'] = response.data['token'];
      } else {
        print("Login failed: ${response.statusCode}");
      }
    } catch (e) {
      print("Error: $e");
    }
  }

  Future<void> _handleSocialLoginButton(BuildContext context) async {
    try {
      final GoogleSignInAccount? googleUser = await _googleSignIn.signIn();
      if (googleUser == null) {
        print('Google login cancelled');
        return;
      }

      final GoogleSignInAuthentication googleAuth =
          await googleUser.authentication;
      final OAuthCredential credential = GoogleAuthProvider.credential(
        accessToken: googleAuth.accessToken,
        idToken: googleAuth.idToken,
      );

      final UserCredential userCredential =
          await _auth.signInWithCredential(credential);
      final User? currentUser = userCredential.user;

      if (currentUser != null) {
        final String? idToken = await currentUser.getIdToken(true);
        await _sendLoginRequestToServer(idToken);
      } else {
        Navigator.push(context,
            MaterialPageRoute(builder: (context) => const LoginPage()));
      }
    } catch (error) {
      if (kDebugMode) {
        print('Google login failed: $error');
      }
    }
  }

  Future<void> _sendLoginRequestToServer(String? idToken) async {
    final dio = Dio();
    try {
      final response = await dio.post(
        'http://localhost:8080/login',
        options: Options(
          headers: {
            'Authorization': 'Bearer $idToken',
            'Social': 'Google',
          },
        ),
      );

      if (response.statusCode == 200) {
        Navigator.pushReplacement(
          context,
          MaterialPageRoute(builder: (context) => Game_Lobby()),
        );
        _storage['token'] = response.data['token'];
      } else {
        if (kDebugMode) {
          print("Login failed: ${response.statusCode}");
        }
      }
    } catch (e) {
      if (kDebugMode) {
        print("Error sending login request to server: $e");
      }
    }
  }

  Widget _buildInputField(String label, TextEditingController controller,
      {bool isPassword = false}) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(label, style: _labelStyle),
        const SizedBox(height: 6),
        Container(
          width: 522,
          padding: const EdgeInsets.symmetric(horizontal: 24),
          decoration: BoxDecoration(
            color: Colors.white,
            borderRadius: BorderRadius.circular(4),
            border: Border.all(width: 1, color: Colors.black.withOpacity(0.1)),
          ),
          child: TextField(
            controller: controller,
            obscureText: isPassword,
            decoration: InputDecoration(
              hintText: label,
              hintStyle:
                  const TextStyle(color: Color(0xFFC1C1C1), fontSize: 20),
              border: InputBorder.none,
            ),
            style: const TextStyle(color: Colors.black, fontSize: 20),
          ),
        ),
      ],
    );
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Container(
          width: 1600,
          height: 960,
          clipBehavior: Clip.antiAlias,
          decoration: const BoxDecoration(color: Colors.white),
          child: Stack(
            children: [
              Positioned(
                left: 154,
                top: 148,
                child: SizedBox(
                  width: 556,
                  height: 707,
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.start,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: [
                      _buildTitle(),
                      const SizedBox(height: 80),
                      _buildLoginForm(),
                    ],
                  ),
                ),
              ),
              _buildBackgroundImage(),
            ],
          ),
        ),
      ],
    );
  }

  Widget _buildTitle() {
    return const Text.rich(
      TextSpan(
        children: [
          TextSpan(
            text: 'soccer ',
            style: TextStyle(
              color: Color(0xFF07021F),
              fontSize: 48,
              fontFamily: 'Literata',
              fontWeight: FontWeight.w500,
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
              letterSpacing: -0.96,
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildLoginForm() {
    return Column(
      children: [
        _buildInputField('Nickname', _nicknameController),
        const SizedBox(height: 24),
        _buildInputField('Password', _passwordController, isPassword: true),
        const SizedBox(height: 10),
        _buildForgotPasswordText(),
        const SizedBox(height: 30),
        _buildLoginButton(),
        const SizedBox(height: 24),
        _buildOrDivider(),
        const SizedBox(height: 24),
        _buildGoogleSignInButton(),
        const SizedBox(height: 30),
        _buildSignUpText(),
      ],
    );
  }

  Widget _buildForgotPasswordText() {
    return const Align(
      alignment: Alignment.centerRight,
      child: Text(
        'Forgot password? ',
        style: TextStyle(
          color: Color(0xFF6153BD),
          fontSize: 20,
          fontFamily: 'Inter',
          fontWeight: FontWeight.w500,
          decoration: TextDecoration.underline,
        ),
      ),
    );
  }

  Widget _buildLoginButton() {
    return InkWell(
      onTap: _handleLoginButton,
      child: Container(
        width: 522,
        height: 60,
        padding: const EdgeInsets.symmetric(horizontal: 24, vertical: 12),
        decoration: ShapeDecoration(
          color: const Color(0xFF120071),
          shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(4)),
        ),
        child: const Center(
          child: Text(
            'Log in',
            style: TextStyle(
              color: Colors.white,
              fontSize: 24,
              fontFamily: 'Inter',
              fontWeight: FontWeight.w500,
            ),
          ),
        ),
      ),
    );
  }

  Widget _buildOrDivider() {
    return const Row(
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        Expanded(child: Divider(color: Color(0x7F7D7D7D), thickness: 2)),
        Padding(
          padding: EdgeInsets.symmetric(horizontal: 24),
          child: Text(
            'or',
            style: TextStyle(
              color: Color(0xFF7D7D7D),
              fontSize: 16,
              fontFamily: 'Inter',
              fontWeight: FontWeight.w500,
            ),
          ),
        ),
        Expanded(child: Divider(color: Color(0x7F7D7D7D), thickness: 2)),
      ],
    );
  }

  Widget _buildGoogleSignInButton() {
    return InkWell(
      onTap: () => _handleSocialLoginButton(context),
      child: Container(
        width: 522,
        height: 60,
        padding: const EdgeInsets.symmetric(horizontal: 24, vertical: 12),
        decoration: ShapeDecoration(
          shape: RoundedRectangleBorder(
            side: const BorderSide(width: 1, color: Color(0x7F07021F)),
            borderRadius: BorderRadius.circular(4),
          ),
        ),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Image.asset('images/Google.png', width: 24, height: 24),
            const SizedBox(width: 12),
            const Text(
              'Sign in with Google',
              style: TextStyle(
                color: Color(0xFF7D7D7D),
                fontSize: 18,
                fontFamily: 'Inter',
                fontWeight: FontWeight.w500,
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildSignUpText() {
    return Row(
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        const Text(
          'Are you new?',
          style: TextStyle(
            color: Color(0xFF7D7D7D),
            fontSize: 20,
            fontFamily: 'Inter',
            fontWeight: FontWeight.w500,
          ),
        ),
        const SizedBox(width: 4),
        InkWell(
          onTap: () {
            Navigator.push(
              context,
              MaterialPageRoute(builder: (context) => const Signup_Page()),
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
            ),
          ),
        ),
      ],
    );
  }

  Widget _buildBackgroundImage() {
    return Positioned(
      left: 850,
      top: -55,
      child: Transform(
        transform: Matrix4.identity()..rotateZ(0.0),
        child: Container(
          width: 751.85,
          height: 1019.13,
          decoration: ShapeDecoration(
            image: const DecorationImage(
              image: AssetImage("images/game.png"),
              fit: BoxFit.fill,
            ),
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(30),
            ),
          ),
        ),
      ),
    );
  }
}
