package self.processing;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class ProcessingCollision extends PApplet {

    int num = 10;
    List<Circle> circles = new ArrayList<Circle>();

    @Override
    public void setup() {

        size(1000, 600);
        background(255);
        smooth();
        strokeWeight(1);
        fill(150, 50);
        drawCircles();
    }

    @Override
    public void draw() {

        background(255);
        for (Circle circle : circles) {
            circle.update();
        }
    }

    @Override
    public void mouseReleased() {

        drawCircles();
    }

    private void drawCircles() {

        for (int i = 0; i < num; i++) {
            Circle circle = new Circle();
            circle.draw();
            circles.add(circle);
        }
    }

    public class Circle {

        float x;
        float y;
        float radius;
        int linecolor;
        int fillcolor;
        float alpha;
        float xmove;
        float ymove;

        public Circle() {

            x = random(width);
            y = random(height);
            radius = random(100) + 10;
            linecolor = color(random(255), random(255), random(255));
            fillcolor = color(random(255), random(255), random(255));
            alpha = random(255);
            xmove = random(10) - 5;
            ymove = random(10) - 5;
        }

        public void draw() {

            noStroke();
            fill(fillcolor, alpha);
            ellipse(x, y, radius * 2, radius * 2);
            stroke(linecolor, 150);
            noFill();
            ellipse(x, y, 10, 10);
        }

        public void update() {

            x += xmove;
            y += ymove;
            if (x > (width + radius)) {
                x = 0 - radius;
            }
            if (x < (0 - radius)) {
                x = width + radius;
            }
            if (y > (height + radius)) {
                y = 0 - radius;
            }
            if (y < (0 - radius)) {
                y = height + radius;
            }

            boolean touching = false;
            for (Circle circle : circles) {
                if (circle != this) {
                    float distance = dist(x, y, circle.x, circle.y);
                    float overlap = distance - radius - circle.radius;
                    
                    if (overlap < 0) {
                        float midx = (x + circle.x) / 2;
                        float midy = (y + circle.y) / 2;
                        stroke(0, 100);
                        noFill();
                        overlap *= -1;
                        ellipse(midx, midy, overlap, overlap);
                    }
                }
            }
            if (touching) {
                if (alpha > 0) {
                    alpha--;
                }
            } else {
                if (alpha < 255) {
                    alpha++;
                }
            }

           // draw();
        }
    }

}
