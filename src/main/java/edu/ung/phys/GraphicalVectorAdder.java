package edu.ung.phys;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.Collections;
import java.util.ArrayList;
import java.io.BufferedReader;

/**
 * @author naharrison
 */
public class GraphicalVectorAdder extends PApplet {

	public static void main(String[] args) {
		PApplet.main("edu.ung.phys.GraphicalVectorAdder");
	}


  public int frRate, pxPerUnit;
  public ArrayList<Double> vxi, vyi, vxf, vyf;

	
	public void settings() {
		size(900, 900);
	}


	public void setup() {
		frRate = 60;
    pxPerUnit = 35;
		frameRate(frameRate);

    BufferedReader dataReader = createReader("vectorsXY.txt");
    Txt2Data txt2d = new Txt2Data(dataReader);
    vxf = txt2d.x;
    vyf = txt2d.y;
    vxi = new ArrayList<Double>(Collections.nCopies(vxf.size(), 0.0));
    vyi = new ArrayList<Double>(Collections.nCopies(vxf.size(), 0.0));
	}

	
	public void draw() {
    background(200);
    stroke(0);

    strokeWeight(4);
    line(0, height/2, width, height/2); // x-axis
    line(width/2, 0, width/2, height); // y-axis

    // grid lines:
    strokeWeight(1);
    for(int k = 1; k <= (width/2)/pxPerUnit; k++) {
      line(width/2 + k*pxPerUnit, 0, width/2 + k*pxPerUnit, height);
      line(width/2 - k*pxPerUnit, 0, width/2 - k*pxPerUnit, height);
    }
    for(int k = 1; k <= (height/2)/pxPerUnit; k++) {
      line(0, height/2 + k*pxPerUnit, width, height/2 + k*pxPerUnit);
      line(0, height/2 - k*pxPerUnit, width, height/2 - k*pxPerUnit);
    }

    // vectors:
    strokeWeight(2);
    for(int k = 0; k < vxi.size(); k++) {
      drawVector(vxi.get(k), vyi.get(k), vxf.get(k), vyf.get(k), k);
    }
	}


  public void drawVector(double xi, double yi, double xf, double yf, int index) {
    if(index == 0) stroke(255, 0, 0);
    else if(index == 1) stroke(0, 255, 0);
    else if(index == 2) stroke(0, 0, 255);
    else stroke(0);
    float pxxi = (float) (width/2 + xi*pxPerUnit);
    float pxyi = (float) (height/2 - yi*pxPerUnit);
    float pxxf = (float) (width/2 + xf*pxPerUnit);
    float pxyf = (float) (height/2 - yf*pxPerUnit);
    
    line(pxxi, pxyi, pxxf, pxyf);

    pushMatrix();
    translate(pxxf, pxyf);
    double theta = Math.atan2(yf - yi, xf - xi);
    rotate((float) -theta);
    line(0, 0, -15, 8);
    line(0, 0, -15, -8);
    popMatrix();
  }

	
	public void mouseDragged() {
    int vindex = -1;

    for(int k = 0; k < vxi.size(); k++) {
      float pxxi = (float) (width/2 + vxi.get(k)*pxPerUnit);
      float pxyi = (float) (height/2 - vyi.get(k)*pxPerUnit);
      float pxxf = (float) (width/2 + vxf.get(k)*pxPerUnit);
      float pxyf = (float) (height/2 - vyf.get(k)*pxPerUnit);
      int dist = (int) Math.sqrt( Math.pow(mouseX - pxxf, 2) + Math.pow(mouseY - pxyf, 2) );
      if(dist < 30) {
        vindex = k;
        break;
      }
    }

    if(vindex >= 0) {
      double coordxf = ((double) (mouseX - width/2)) / ((double) pxPerUnit);
      double coordyf = ((double) (height/2 - mouseY)) / ((double) pxPerUnit);
      double coordxi = vxi.get(vindex) + (coordxf - vxf.get(vindex));
      double coordyi = vyi.get(vindex) + (coordyf - vyf.get(vindex));
      vxf.set(vindex, coordxf);
      vyf.set(vindex, coordyf);
      vxi.set(vindex, coordxi);
      vyi.set(vindex, coordyi);
    }
	}
	
}
