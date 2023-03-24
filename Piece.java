import java.util.LinkedList;

public class Piece {
    int xPosition;
    int yPosition;
    int xPositionNorm;
    int yPositionNorm;
    boolean isWhite;
    boolean firstMove = true;
    LinkedList<Piece> piece;
    String name;
    int[][] validMoves = new int[8][8];

    public Piece(int xPosition, int yPosition, boolean isWhite, String n, LinkedList<Piece> ps){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        xPositionNorm = xPosition*100;
        yPositionNorm = yPosition*100;
        this.isWhite = isWhite;
        this.piece = ps;
        this.name = n;
        this.initialize();
        ps.add(this);
    }

    public void move(int xPosition, int yPosition) {
        if(Chess.getPiece(xPosition*100, yPosition*100) != null) {
            if((validMoves[xPosition][yPosition] == 2)) {
                Chess.getPiece(xPosition*100, yPosition*100).kill();
            }else {
                xPosition = this.xPosition*100;
                yPosition = this.yPosition*100;
                return;
            }
        } else {
            if((validMoves[xPosition][yPosition] == 0)) {
                xPosition = this.xPosition*100;
                yPosition = this.yPosition*100;
                return;
            }
        }
        this.firstMove = false;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        xPositionNorm = xPosition*100;
        yPositionNorm = yPosition*100;
    }

    public void initialize() {
        for(int y = 0; y < 8; y++) {
            for(int x = 0; x < 8; x++) {
                validMoves[x][y] = 0;
            }
        }
    }

    public int[][] validMoves() {
        if(name == "pawn") {
            moveCheckPawn(xPosition, yPosition, isWhite);
        } else if (name == "rook") {
            moveCheckRook(xPosition, yPosition);
        } else if (name == "knight") {
            moveCheckKnight(xPosition, yPosition);
        }else if (name == "bishop") {
            moveCheckBishop(xPosition, yPosition);
        } else if (name == "queen") {
            moveCheckQueen(xPosition, yPosition);
        } else if (name == "king") {
            moveCheckKing(xPosition, yPosition);
        }
        return validMoves;
    }

    public void moveCheckQueen(int xp, int yp) {
        up(xp, yp);
        down(xp, yp);
        left(xp, yp);  
        right(xp, yp);
        diagonalUpRight(xp, yp);
        diagonalUpLeft(xp, yp);
        diagonalDownRight(xp, yp);
        diagonalDownLeft(xp, yp);
    }

    public void moveCheckRook(int xp, int yp) {
        up(xp, yp);
        down(xp, yp);
        left(xp, yp);  
        right(xp, yp);
    }

    public void moveCheckBishop(int xp, int yp) {
        diagonalUpRight(xp, yp);
        diagonalUpLeft(xp, yp);
        diagonalDownRight(xp, yp);
        diagonalDownLeft(xp, yp);
    }

    public void moveCheckKnight(int xp, int yp) {
        upL(xp, yp);
        downL(xp, yp);
        leftL(xp, yp);
        rightL(xp, yp);
    }

    public void moveCheckPawn(int xp, int yp, boolean isWhite) {
        if(isWhite) {
            if(firstMove) {
                if((Chess.getPiece((xp)*100, (yp-2)*100) == null)) {
                    this.validMoves[xp][yp-2] = -1;
                }
            }
            if((Chess.getPiece((xp)*100, (yp-1)*100) == null)) {
                this.validMoves[xp][yp-1] = -1;
            }
            if(Chess.getPiece((xp-1)*100, (yp-1)*100) != null) {
                if((Chess.getPiece((xp-1)*100, (yp-1)*100).isWhite != isWhite)) {
                    this.validMoves[xp-1][yp-1] = 2;
                }
            }
            if(Chess.getPiece((xp+1)*100, (yp-1)*100) != null) {
                if((Chess.getPiece((xp+1)*100, (yp-1)*100).isWhite != isWhite)) {
                    this.validMoves[xp+1][yp-1] = 2;
                }
            }
        } else {
            if(firstMove) {
                if((Chess.getPiece((xp)*100, (yp+2)*100) == null)) {
                    this.validMoves[xp][yp+2] = -1;
                }
            }
            if((Chess.getPiece((xp)*100, (yp+1)*100) == null)) {
                this.validMoves[xp][yp+1] = -1;
            }
            if(Chess.getPiece((xp-1)*100, (yp+1)*100) != null) {
                if((Chess.getPiece((xp-1)*100, (yp+1)*100).isWhite != isWhite)) {
                    this.validMoves[xp-1][yp+1] = 2;
                }
            }
            if(Chess.getPiece((xp+1)*100, (yp+1)*100) != null) {
                if((Chess.getPiece((xp+1)*100, (yp+1)*100).isWhite != isWhite)) {
                    this.validMoves[xp+1][yp+1] = 2;
                }
            }
        }
    }

    public void moveCheckKing(int xp, int yp) {
        for(int y = -1; y < 2; y++) {
            for(int x = -1; x < 2; x++) {
                if((yp+y >= 0) && (yp+y < 8) && (xp+x >= 0) && (xp+x < 8)) {
                    if(Chess.getPiece((xp+x)*100, (yp+y)*100) == null) {
                        this.validMoves[xp+x][yp+y] = -1;
                    } else if(Chess.getPiece((xp+x)*100, (yp+y)*100).isWhite != isWhite) {
                        this.validMoves[xp+x][yp+y] = 2;
                    }
                }
            }
        }
    }

    public void upL(int xp, int yp) {
        int xpTemp = xp;
        int ypTemp = yp;
        xp += 1;
        yp -= 2;
        if((xp < 8) && (yp >= 0)) {
            if(Chess.getPiece((xp)*100, (yp)*100) == null) {
                this.validMoves[xp][yp] = -1;
            } else if(Chess.getPiece((xp)*100, (yp)*100).isWhite != isWhite) {
                this.validMoves[xp][yp] = 2;
            }
        }
        xp = xpTemp;
        yp = ypTemp;
        xp -= 1;
        yp -= 2;
        if((xp >= 0) && (yp >= 0)) {
            if(Chess.getPiece((xp)*100, (yp)*100) == null) {
                this.validMoves[xp][yp] = -1;
            } else if(Chess.getPiece((xp)*100, (yp)*100).isWhite != isWhite) {
                this.validMoves[xp][yp] = 2;
            }
        }
    }

    public void downL(int xp, int yp) {
        int xpTemp = xp;
        int ypTemp = yp;
        xp += 1;
        yp += 2;
        if((xp < 8) && (yp < 8)) {
            if(Chess.getPiece((xp)*100, (yp)*100) == null) {
                this.validMoves[xp][yp] = -1;
            } else if(Chess.getPiece((xp)*100, (yp)*100).isWhite != isWhite) {
                this.validMoves[xp][yp] = 2;
            }
        }
        xp = xpTemp;
        yp = ypTemp;
        xp -= 1;
        yp += 2;
        if((xp >= 0) && (yp < 8)) {
            if(Chess.getPiece((xp)*100, (yp)*100) == null) {
                this.validMoves[xp][yp] = -1;
            } else if(Chess.getPiece((xp)*100, (yp)*100).isWhite != isWhite) {
                this.validMoves[xp][yp] = 2;
            }
        }
    }

    public void leftL(int xp, int yp) {
        int xpTemp = xp;
        int ypTemp = yp;
        xp -= 2;
        yp += 1;
        if((xp >= 0) && (yp < 8)) {
            if(Chess.getPiece((xp)*100, (yp)*100) == null) {
                this.validMoves[xp][yp] = -1;
            } else if(Chess.getPiece((xp)*100, (yp)*100).isWhite != isWhite) {
                this.validMoves[xp][yp] = 2;
            }
        }
        xp = xpTemp;
        yp = ypTemp;
        xp -= 2;
        yp -= 1;
        if((xp >= 0) && (yp >= 0)) {
            if(Chess.getPiece((xp)*100, (yp)*100) == null) {
                this.validMoves[xp][yp] = -1;
            } else if(Chess.getPiece((xp)*100, (yp)*100).isWhite != isWhite) {
                this.validMoves[xp][yp] = 2;
            }
        }
    }

    public void rightL(int xp, int yp) {
        int xpTemp = xp;
        int ypTemp = yp;
        xp += 2;
        yp += 1;
        if((xp < 8) && (yp < 8)) {
            if(Chess.getPiece((xp)*100, (yp)*100) == null) {
                this.validMoves[xp][yp] = -1;
            } else if(Chess.getPiece((xp)*100, (yp)*100).isWhite != isWhite) {
                this.validMoves[xp][yp] = 2;
            }
        }
        xp = xpTemp;
        yp = ypTemp;
        xp += 2;
        yp -= 1;
        if((xp < 8) && (yp >= 0)) {
            if(Chess.getPiece((xp)*100, (yp)*100) == null) {
                this.validMoves[xp][yp] = -1;
            } else if(Chess.getPiece((xp)*100, (yp)*100).isWhite != isWhite) {
                this.validMoves[xp][yp] = 2;
            }
        }
    }

    public void diagonalUpRight(int xp, int yp) {
        if(Chess.getPiece((xp+1)*100, (yp-1)*100) == null) {
            if((yp-1 >= 0) && (xp+1 < 8)) {
                yp -= 1;
                xp += 1;
                this.validMoves[xp][yp] = -1;
                diagonalUpRight(xp, yp);
            }
        } else if(Chess.getPiece((xp+1)*100, (yp-1)*100).isWhite != isWhite) {
            yp -= 1;
            xp += 1;
            this.validMoves[xp][yp] = 2;
        }
    }

    public void diagonalDownRight(int xp, int yp) {
        if(Chess.getPiece((xp+1)*100, (yp+1)*100) == null) {
            if((yp+1 < 8) && (xp+1 < 8)) {
                yp += 1;
                xp += 1;
                this.validMoves[xp][yp] = -1;
                diagonalDownRight(xp, yp);
            }
        } else if(Chess.getPiece((xp+1)*100, (yp+1)*100).isWhite != isWhite) {
            yp += 1;
            xp += 1;
            this.validMoves[xp][yp] = 2;
        }
    }

    public void diagonalUpLeft(int xp, int yp) {
        if(Chess.getPiece((xp-1)*100, (yp-1)*100) == null) {
            if((yp-1 >= 0) && (xp-1 >= 0)) {
                yp -= 1;
                xp -= 1;
                this.validMoves[xp][yp] = -1;
                diagonalUpLeft(xp, yp);
            }
        } else if(Chess.getPiece((xp-1)*100, (yp-1)*100).isWhite != isWhite) {
            yp -= 1;
            xp -= 1;
            this.validMoves[xp][yp] = 2;
        }
    }

    public void diagonalDownLeft(int xp, int yp) {
        if(Chess.getPiece((xp-1)*100, (yp+1)*100) == null) {
            if((yp+1 < 8) && (xp-1 >= 0)) {
                yp += 1;
                xp -= 1;
                this.validMoves[xp][yp] = -1;
                diagonalDownLeft(xp, yp);
            }
        } else if(Chess.getPiece((xp-1)*100, (yp+1)*100).isWhite != isWhite) {
            yp += 1;
            xp -= 1;
            this.validMoves[xp][yp] = 2;
        }
    }

    public void up(int xp, int yp) {
        if(Chess.getPiece((xp)*100, (yp-1)*100) == null) {
            if(yp-1 >= 0) {
                yp -= 1;
                this.validMoves[xp][yp] = -1;
                up(xp, yp);
            }
        } else if(Chess.getPiece((xp)*100, (yp-1)*100).isWhite != isWhite) {
            yp -= 1;
            this.validMoves[xp][yp] = 2;
        }
    }

    public void down(int xp, int yp) {
        if(Chess.getPiece((xp)*100, (yp+1)*100) == null) {
            if(yp+1 < 8) {
                yp += 1;
                this.validMoves[xp][yp] = -1;
                down(xp, yp);
            }
        } else if(Chess.getPiece((xp)*100, (yp+1)*100).isWhite != isWhite) {
            yp += 1;
            this.validMoves[xp][yp] = 2;
        }
    }

    public void left(int xp, int yp) {
        if(Chess.getPiece((xp-1)*100, (yp)*100) == null) {
            if(xp-1 >= 0) {
                xp -= 1;
                this.validMoves[xp][yp] = -1;
                left(xp, yp);
            }
        } else if(Chess.getPiece((xp-1)*100, (yp)*100).isWhite != isWhite) {
            xp -= 1;
            this.validMoves[xp][yp] = 2;
        }
    }

    public void right(int xp, int yp) {
        if(Chess.getPiece((xp+1)*100, (yp)*100) == null) {
            if(xp+1 < 8) { 
                xp += 1;
                this.validMoves[xp][yp] = -1;
                right(xp, yp);
            }
        } else if(Chess.getPiece((xp+1)*100, (yp)*100).isWhite != isWhite) {
            xp += 1;
            this.validMoves[xp][yp] = 2;
        }
    }

    public void kill(){
        piece.remove(this);
    }
}
