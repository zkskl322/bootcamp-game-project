import 'package:flutter/material.dart';



class IngameLobby extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Container(
          width: 1600,
          height: 960,
          clipBehavior: Clip.antiAlias,
          decoration: BoxDecoration(color: Color(0xFFF2F2F2)),
          child: Stack(
            children: [
              Positioned(
                left: 800,
                top: 32,
                child: Container(
                  width: 765,
                  height: 432,
                  child: Stack(
                    children: [
                      Positioned(
                        left: 0,
                        top: 0,
                        child: Container(
                          width: 355,
                          height: 356,
                          decoration: BoxDecoration(color: Color(0xFFD9D9D9)),
                        ),
                      ),
                      Positioned(
                        left: 410,
                        top: 0,
                        child: Container(
                          width: 355,
                          height: 356,
                          decoration: BoxDecoration(color: Color(0xFFD9D9D9)),
                        ),
                      ),
                      Positioned(
                        left: 66.71,
                        top: 21.36,
                        child: Container(
                          width: 221.41,
                          height: 313.27,
                          child: Stack(),
                        ),
                      ),
                      Positioned(
                        left: 444,
                        top: 31,
                        child: Container(
                          width: 288,
                          height: 294,
                          child: Stack(),
                        ),
                      ),
                      Positioned(
                        left: 115,
                        top: 394,
                        child: Text(
                          'MARIO',
                          style: TextStyle(
                            color: Colors.black,
                            fontSize: 24,
                            fontFamily: 'Press Start 2P',
                            fontWeight: FontWeight.w400,
                            height: 0.07,
                            letterSpacing: 0.96,
                          ),
                        ),
                      ),
                      Positioned(
                        left: 522,
                        top: 394,
                        child: Text(
                          'KOOPA',
                          style: TextStyle(
                            color: Colors.black,
                            fontSize: 24,
                            fontFamily: 'Press Start 2P',
                            fontWeight: FontWeight.w400,
                            height: 0.07,
                            letterSpacing: 0.96,
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
              Positioned(
                left: 30,
                top: 45,
                child: Container(
                  width: 690,
                  height: 870,
                  clipBehavior: Clip.antiAlias,
                  decoration: ShapeDecoration(
                    color: Color(0xFF1F0707),
                    shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(8)),
                  ),
                  child: Stack(
                    children: [
                      Positioned(
                        left: 39,
                        top: 143,
                        child: Container(
                          height: 150,
                          padding: const EdgeInsets.only(left: 14, right: 95),
                          clipBehavior: Clip.antiAlias,
                          decoration: ShapeDecoration(
                            color: Colors.white,
                            shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(4)),
                          ),
                          child: Row(
                            mainAxisSize: MainAxisSize.min,
                            mainAxisAlignment: MainAxisAlignment.start,
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Text(
                                'TARGET GOAL',
                                style: TextStyle(
                                  color: Colors.black,
                                  fontSize: 20,
                                  fontFamily: 'Press Start 2P',
                                  fontWeight: FontWeight.w400,
                                  height: 0.09,
                                  letterSpacing: 0.80,
                                ),
                              ),
                              const SizedBox(width: 234),
                              Text(
                                '10',
                                style: TextStyle(
                                  color: Colors.black,
                                  fontSize: 20,
                                  fontFamily: 'Press Start 2P',
                                  fontWeight: FontWeight.w400,
                                  height: 0.09,
                                  letterSpacing: 0.80,
                                ),
                              ),
                            ],
                          ),
                        ),
                      ),
                      Positioned(
                        left: 39,
                        top: 323,
                        child: Container(
                          height: 150,
                          padding: const EdgeInsets.only(left: 14, right: 78),
                          clipBehavior: Clip.antiAlias,
                          decoration: ShapeDecoration(
                            color: Colors.white,
                            shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(4)),
                          ),
                          child: Row(
                            mainAxisSize: MainAxisSize.min,
                            mainAxisAlignment: MainAxisAlignment.start,
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Text(
                                'LIMITED TIME',
                                style: TextStyle(
                                  color: Colors.black,
                                  fontSize: 20,
                                  fontFamily: 'Press Start 2P',
                                  fontWeight: FontWeight.w400,
                                  height: 0.09,
                                  letterSpacing: 0.80,
                                ),
                              ),
                              const SizedBox(width: 167),
                              Text(
                                '5 MIN',
                                style: TextStyle(
                                  color: Colors.black,
                                  fontSize: 20,
                                  fontFamily: 'Press Start 2P',
                                  fontWeight: FontWeight.w400,
                                  height: 0.09,
                                  letterSpacing: 0.80,
                                ),
                              ),
                            ],
                          ),
                        ),
                      ),
                      Positioned(
                        left: 39,
                        top: 503,
                        child: Container(
                          height: 150,
                          padding: const EdgeInsets.only(left: 14, right: 83),
                          clipBehavior: Clip.antiAlias,
                          decoration: ShapeDecoration(
                            color: Colors.white,
                            shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(4)),
                          ),
                          child: Row(
                            mainAxisSize: MainAxisSize.min,
                            mainAxisAlignment: MainAxisAlignment.start,
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Text(
                                'ROOM PASSWORD',
                                style: TextStyle(
                                  color: Colors.black,
                                  fontSize: 20,
                                  fontFamily: 'Press Start 2P',
                                  fontWeight: FontWeight.w400,
                                  height: 0.09,
                                  letterSpacing: 0.80,
                                ),
                              ),
                              const SizedBox(width: 162),
                              Text(
                                '1234',
                                style: TextStyle(
                                  color: Colors.black,
                                  fontSize: 20,
                                  fontFamily: 'Press Start 2P',
                                  fontWeight: FontWeight.w400,
                                  height: 0.09,
                                  letterSpacing: 0.80,
                                ),
                              ),
                            ],
                          ),
                        ),
                      ),
                      Positioned(
                        left: 39,
                        top: 683,
                        child: Container(
                          height: 150,
                          padding: const EdgeInsets.only(left: 14, right: 78),
                          clipBehavior: Clip.antiAlias,
                          decoration: ShapeDecoration(
                            color: Colors.white,
                            shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(4)),
                          ),
                          child: Row(
                            mainAxisSize: MainAxisSize.min,
                            mainAxisAlignment: MainAxisAlignment.start,
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Text(
                                'LIMITED SIZE',
                                style: TextStyle(
                                  color: Colors.black,
                                  fontSize: 20,
                                  fontFamily: 'Press Start 2P',
                                  fontWeight: FontWeight.w400,
                                  height: 0.09,
                                  letterSpacing: 0.80,
                                ),
                              ),
                              const SizedBox(width: 251),
                              Text(
                                '2',
                                style: TextStyle(
                                  color: Colors.black,
                                  fontSize: 20,
                                  fontFamily: 'Press Start 2P',
                                  fontWeight: FontWeight.w400,
                                  height: 0.09,
                                  letterSpacing: 0.80,
                                ),
                              ),
                            ],
                          ),
                        ),
                      ),
                      Positioned(
                        left: 39,
                        top: 54,
                        child: SizedBox(
                          width: 612,
                          height: 47,
                          child: Text(
                            'GAME SETTING',
                            textAlign: TextAlign.center,
                            style: TextStyle(
                              color: Colors.white,
                              fontSize: 40,
                              fontFamily: 'Press Start 2P',
                              fontWeight: FontWeight.w400,
                              height: 0.02,
                              letterSpacing: 1.60,
                            ),
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
              Positioned(
                left: 800,
                top: 483,
                child: Container(
                  width: 765,
                  height: 329,
                  clipBehavior: Clip.antiAlias,
                  decoration: ShapeDecoration(
                    color: Color(0xFF1F0707),
                    shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(8)),
                  ),
                ),
              ),
              Positioned(
                left: 800,
                top: 308,
                child: Container(
                  width: 765,
                  height: 80,
                  clipBehavior: Clip.antiAlias,
                  decoration: BoxDecoration(),
                  child: Row(
                    mainAxisSize: MainAxisSize.min,
                    mainAxisAlignment: MainAxisAlignment.center,
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Expanded(
                        child: Container(
                          height: double.infinity,
                          padding: const EdgeInsets.only(
                            top: 21,
                            left: 73,
                            right: 74,
                            bottom: 21,
                          ),
                          clipBehavior: Clip.antiAlias,
                          decoration: BoxDecoration(color: Color(0xFF9F9F9F)),
                          child: Row(
                            mainAxisSize: MainAxisSize.min,
                            mainAxisAlignment: MainAxisAlignment.center,
                            crossAxisAlignment: CrossAxisAlignment.center,
                            children: [
                              Text(
                                'READY',
                                style: TextStyle(
                                  color: Color(0xFF9B3535),
                                  fontSize: 40,
                                  fontFamily: 'Press Start 2P',
                                  fontWeight: FontWeight.w400,
                                  height: 0.02,
                                  letterSpacing: 1.60,
                                ),
                              ),
                            ],
                          ),
                        ),
                      ),
                      const SizedBox(width: 58),
                      Container(
                        width: 353,
                        height: 80,
                        clipBehavior: Clip.antiAlias,
                        decoration: BoxDecoration(),
                      ),
                    ],
                  ),
                ),
              ),
              Positioned(
                left: 855,
                top: 816,
                child: Container(
                  width: 655,
                  padding: const EdgeInsets.only(top: 20, right: 1),
                  clipBehavior: Clip.antiAlias,
                  decoration: BoxDecoration(color: Color(0xFFEAEAEA)),
                  child: Row(
                    mainAxisSize: MainAxisSize.min,
                    mainAxisAlignment: MainAxisAlignment.center,
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Expanded(
                        child: Container(
                          height: double.infinity,
                          padding: const EdgeInsets.symmetric(horizontal: 33, vertical: 21),
                          clipBehavior: Clip.antiAlias,
                          decoration: ShapeDecoration(
                            color: Color(0xFF34802D),
                            shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(30),
                            ),
                          ),
                          child: Row(
                            mainAxisSize: MainAxisSize.min,
                            mainAxisAlignment: MainAxisAlignment.center,
                            crossAxisAlignment: CrossAxisAlignment.center,
                            children: [
                              Text(
                                'READY',
                                style: TextStyle(
                                  color: Colors.black,
                                  fontSize: 40,
                                  fontFamily: 'Press Start 2P',
                                  fontWeight: FontWeight.w400,
                                  height: 0.02,
                                  letterSpacing: 1.60,
                                ),
                              ),
                            ],
                          ),
                        ),
                      ),
                      const SizedBox(width: 108),
                      Expanded(
                        child: Container(
                          height: double.infinity,
                          padding: const EdgeInsets.symmetric(horizontal: 33, vertical: 21),
                          clipBehavior: Clip.antiAlias,
                          decoration: ShapeDecoration(
                            color: Color(0xFF4E59C0),
                            shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(30),
                            ),
                          ),
                          child: Row(
                            mainAxisSize: MainAxisSize.min,
                            mainAxisAlignment: MainAxisAlignment.center,
                            crossAxisAlignment: CrossAxisAlignment.center,
                            children: [
                              Text(
                                'START',
                                style: TextStyle(
                                  color: Colors.black,
                                  fontSize: 40,
                                  fontFamily: 'Press Start 2P',
                                  fontWeight: FontWeight.w400,
                                  height: 0.02,
                                  letterSpacing: 1.60,
                                ),
                              ),
                            ],
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ],
          ),
        ),
      ],
    );
  }
}