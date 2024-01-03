import processing.core.PApplet;

public class Sketch extends PApplet {

  float[] snowflakeX = new float[25];
  float[] snowflakeY = new float[25];
  float[] snowflakeSize = new float[25];
  float snowSpeed = 1.0f; 

  float playerX, playerY;
  int playerLives = 3;

  boolean[] ballHideStatus = new boolean[25]; 

  public void settings() {
    size(400, 300);
    for (int i = 0; i < snowflakeX.length; i++) {
      snowflakeX[i] = random(width);
      snowflakeY[i] = random(height);
      snowflakeSize[i] = random(10, 30); 
    }
  }

  public void setup() {
    background(50);
    playerX = width / 2;
    playerY = height - 20;
  }

  public void draw() {
    background(50); 


    drawLivesIndicator();


    fill(0, 0, 255); 
    ellipse(playerX, playerY, 30, 30);

    checkCollision();

    drawSnowflakes();

    moveSnowflakes();
  }

  private void drawLivesIndicator() {
    float startX = width - 50;
    float startY = 20;
    float squareSize = 20;

    for (int i = 0; i < playerLives; i++) {
      fill(255);
      rect(startX + i * (squareSize + 5), startY, squareSize, squareSize);
    }
  }

  private void drawSnowflakes() {
    for (int i = 0; i < snowflakeX.length; i++) {
      if (!ballHideStatus[i]) {
        fill(255);
        noStroke();
        ellipse(snowflakeX[i], snowflakeY[i], snowflakeSize[i], snowflakeSize[i]);
      }
    }
  }

  private void moveSnowflakes() {
    for (int i = 0; i < snowflakeX.length; i++) {
      if (!ballHideStatus[i]) {
        snowflakeY[i] += snowSpeed;

        if (snowflakeY[i] > height) {
          snowflakeY[i] = 0;
          snowflakeX[i] = random(width);
        }
      }
    }
  }

  private void checkCollision() {
    for (int i = 0; i < snowflakeX.length; i++) {
      if (!ballHideStatus[i]) {
        float distance = dist(playerX, playerY, snowflakeX[i], snowflakeY[i]);
        if (distance < 15) {
          playerLives--;


          playerX = width / 2;
          playerY = height - 20;

          if (playerLives == 0) {
            endGame();
          }
        }
      }
    }
  }

  private void endGame() {
    background(255);
    fill(0);
    textSize(32);
    textAlign(CENTER, CENTER);
    text("Game Over", width / 2, height / 2);
    noLoop(); 
  }

  public void mousePressed() {
    for (int i = 0; i < snowflakeX.length; i++) {
      if (dist(mouseX, mouseY, snowflakeX[i], snowflakeY[i]) < snowflakeSize[i] / 2) {
        ballHideStatus[i] = true;
      }
    }
  }

  public void keyPressed() {
    float playerSpeed = 5;

    if (key == 'A' || key == 'a') {
      playerX -= playerSpeed;
    } else if (key == 'D' || key == 'd') {
      playerX += playerSpeed;
    } else if (key == 'W' || key == 'w') {
      playerY -= playerSpeed;
    } else if (key == 'S' || key == 's') {
      playerY += playerSpeed;
    } else if (keyCode == UP) {
      snowSpeed += 0.1;
    } else if (keyCode == DOWN) {
      snowSpeed -= 0.1;
    }
  }

  public static void main(String[] args) {
    PApplet.main("Sketch");
  }
}
