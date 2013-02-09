package self.processing;

import processing.core.PApplet;

public class ProcessingGOL extends PApplet {

    Cell[][] cells;
    int cellSize = 10;
    int countX;
    int countY;

    @Override
    public void setup() {

        frameRate(15);
        size(500, 300);
        countX = floor(width / cellSize);
        countY = floor(height / cellSize);
        restart();
    }

    public void restart() {

        cells = new Cell[countX][countY];
        for (int x = 0; x < countX; x++) {
            for (int y = 0; y < countY; y++) {
                cells[x][y] = new Cell(x, y);
            }
        }

        for (int x = 0; x < countX; x++) {
            for (int y = 0; y < countY; y++) {
                int above = y - 1;
                int below = y + 1;
                int left = x - 1;
                int right = x + 1;

                if (above < 0) {
                    above = countY - 1;
                }
                if (below == countY) {
                    below = 0;
                }
                if (left < 0) {
                    left = countX - 1;
                }
                if (right == countX) {
                    right = 0;
                }
                cells[x][y].addNeighbor(cells[left][above]);
                cells[x][y].addNeighbor(cells[left][y]);
                cells[x][y].addNeighbor(cells[left][below]);
                cells[x][y].addNeighbor(cells[x][below]);
                cells[x][y].addNeighbor(cells[x][above]);
                cells[x][y].addNeighbor(cells[right][above]);
                cells[x][y].addNeighbor(cells[right][y]);
                cells[x][y].addNeighbor(cells[right][below]);
            }
        }
    }

    @Override
    public void draw() {

        background(200);

        for (int x = 0; x < countX; x++) {
            for (int y = 0; y < countY; y++) {
                cells[x][y].calculateNextState();
            }
        }

        translate(cellSize / 2, cellSize / 2);

        for (int x = 0; x < countX; x++) {
            for (int y = 0; y < countY; y++) {
                cells[x][y].draw();
            }
        }
    }

    @Override
    public void mousePressed() {

        restart();
    }

    public class Cell {

        float x;
        float y;
        boolean state;
        boolean nextState;
        Cell[] neighbors;

        public Cell(float x, float y) {

            this.x = x * cellSize;
            this.y = y * cellSize;

            nextState = random(2) > 1;
            state = nextState;
            neighbors = new Cell[0];
        }

        public void addNeighbor(Cell cell) {

            neighbors = (Cell[]) append(neighbors, cell);
        }

        public void calculateNextState() {

            int liveCount = 0;
            for (Cell neighbor : neighbors) {
                if (neighbor.state) {
                    liveCount++;
                }
            }

            if (state) {
                nextState = liveCount == 2 || liveCount == 3;
            } else {
                nextState = liveCount == 3;
            }
        }

        public void draw() {

            state = nextState;
            stroke(0);
            if (state) {
                fill(0);
            } else {
                fill(255);
            }
            ellipse(x, y, cellSize, cellSize);
        }
    }

}
