package ru.unn.agile.Triangle.Model;

import org.junit.Test;

import java.awt.geom.Point2D;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class TriangleTest {
    @Test
    public void canGetPointA() {
        Point2D a = new Point2D.Double(5, 4);
        Point2D b = new Point2D.Double(1, 0);
        Point2D c = new Point2D.Double(1, 1);

        Triangle triangle = new Triangle(a, b, c);

        Point2D pointA = new Point2D.Double(5, 4);

        assertEquals(pointA, triangle.getPointA());
    }

    @Test
    public void canGetPointB() {
        Point2D a = new Point2D.Double(0, 0);
        Point2D b = new Point2D.Double(1, -1);
        Point2D c = new Point2D.Double(0, 1);

        Triangle triangle = new Triangle(a, b, c);

        Point2D pointB = new Point2D.Double(1, -1);

        assertEquals(pointB, triangle.getPointB());
    }

    @Test
    public void canGetPointC() {
        Point2D a = new Point2D.Double(0, 0);
        Point2D b = new Point2D.Double(1, 0);
        Point2D c = new Point2D.Double(3, 1);

        Triangle triangle = new Triangle(a, b, c);

        Point2D pointC = new Point2D.Double(3, 1);

        assertEquals(pointC, triangle.getPointC());
    }


    @Test
    public void canCreateTriangle2D() {
        Point2D a = new Point2D.Double(5, 4);
        Point2D b = new Point2D.Double(1, 3);
        Point2D c = new Point2D.Double(0, 1);

        Triangle triangle = new Triangle(a, b, c);

        assertNotNull(triangle);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwingExceptionAfterCreatingDegeneratedTriangle() {
        Point2D a = new Point2D.Double(0, 0);
        Point2D b = new Point2D.Double(0, 0);
        Point2D c = new Point2D.Double(0, 1);
        new Triangle(a, b, c);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwingExceptionAfterCreatingTriangleWithPointsOnOneLine() {
        Point2D a = new Point2D.Double(0, 0);
        Point2D b = new Point2D.Double(-1, -1);
        Point2D c = new Point2D.Double(1, 1);
        new Triangle(a, b, c);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwingExceptionAfterCreatingTriangleWithPointsOnOneLineVer2() {
        Point2D a = new Point2D.Double(0, 0);
        Point2D b = new Point2D.Double(0, 1);
        Point2D c = new Point2D.Double(0, 2);
        new Triangle(a, b, c);
    }

    @Test
    public void canGetLengthAB() {
        Point2D a = new Point2D.Double(0, 0);
        Point2D b = new Point2D.Double(1, 0);
        Point2D c = new Point2D.Double(0, 1);
        double targetLength = 1;
        Triangle triangle = new Triangle(a, b, c);

        double lengthAB = triangle.getLengthAB();
        assertEquals(lengthAB, targetLength, 1e-15);
    }

    @Test
    public void canGetLengthABWithNegtiveCoordinates() {
        Point2D a = new Point2D.Double(0, 0);
        Point2D c = new Point2D.Double(0, 1);
        Point2D b = new Point2D.Double(-1, 0);
        Triangle triangle = new Triangle(a, b, c);

        double lengthAB = triangle.getLengthAB();
        assertEquals(lengthAB, 1, 1e-15);
    }

    @Test
    public void canGetLengthABWithDifferentCoordinates() {
        Point2D a = new Point2D.Double(1, 1);
        Point2D b = new Point2D.Double(-1, 1);
        Point2D c = new Point2D.Double(0, 0);
        Triangle triangle = new Triangle(a, b, c);

        double lengthAB = triangle.getLengthAB();
        assertEquals(lengthAB, 2, 1e-15);
    }

    @Test
    public void canGetLengthBC() {
        Point2D a = new Point2D.Double(0, 0);
        Point2D b = new Point2D.Double(1, 0);
        Point2D c = new Point2D.Double(1, 1);
        Triangle triangle = new Triangle(a, b, c);

        double lengthBC = triangle.getLengthBC();
        assertEquals(lengthBC, 1, 1e-15);
    }

    @Test
    public void canGetLengthBCWithNegativeCoordinates() {
        Point2D a = new Point2D.Double(0, 1);
        Point2D b = new Point2D.Double(-1, 0);
        Point2D c = new Point2D.Double(1, 0);
        Triangle triangle = new Triangle(a, b, c);

        double lengthBC = triangle.getLengthBC();
        assertEquals(lengthBC, 2, 1e-15);
    }

    @Test
    public void canGetLengthBCWithDifferentCoordinates() {
        Point2D a = new Point2D.Double(0, 0);
        Point2D b = new Point2D.Double(-1, 1);
        Point2D c = new Point2D.Double(1, 1);
        Triangle triangle = new Triangle(a, b, c);

        double lengthBC = triangle.getLengthBC();
        assertEquals(lengthBC, 2, 1e-15);
    }

    @Test
    public void canGetLengthAC() {
        Point2D a = new Point2D.Double(0, 1);
        Point2D b = new Point2D.Double(1, 0);
        Point2D c = new Point2D.Double(0, 0);
        Triangle triangle = new Triangle(a, b, c);

        double lengthAC = triangle.getLengthAC();
        assertEquals(lengthAC, 1, 1e-15);
    }

    @Test
    public void canGetLengthACWithNegatedCoordinates() {
        Point2D a = new Point2D.Double(0, 0);
        Point2D b = new Point2D.Double(0, 1);
        Point2D c = new Point2D.Double(-1, 0);
        Triangle triangle = new Triangle(a, b, c);

        double lengthAC = triangle.getLengthAC();
        assertEquals(lengthAC, 1, 1e-15);
    }

    @Test
    public void canGetLengthACWithDifferentCoordinates() {
        Point2D a = new Point2D.Double(1, 1);
        Point2D b = new Point2D.Double(0, 0);
        Point2D c = new Point2D.Double(-1, 1);
        Triangle triangle = new Triangle(a, b, c);

        double lengthAC = triangle.getLengthAC();
        assertEquals(lengthAC, 2, 1e-15);
    }

    @Test
    public void canGetPerimeter() {
        Point2D a = new Point2D.Double(1, 0);
        Point2D b = new Point2D.Double(0, 1);
        Point2D c = new Point2D.Double(0, 0);
        Triangle triangle = new Triangle(a, b, c);

        double perimeter = triangle.getPerimeter();
        assertEquals(perimeter, 2 + Math.sqrt(2), 1e-15);
    }

    @Test
    public void canGetPerimeterWithNegativeCoordinates() {
        Point2D a = new Point2D.Double(0, 0);
        Point2D b = new Point2D.Double(-1, -1);
        Point2D c = new Point2D.Double(0, 1);
        Triangle triangle = new Triangle(a, b, c);

        double perimeter = triangle.getPerimeter();
        assertEquals(perimeter, 1 + Math.sqrt(2) + Math.sqrt(5), 1e-15);
    }

    @Test
    public void canGetPerimeterWithDifferentCoordinates() {
        Point2D a = new Point2D.Double(1, 1);
        Point2D b = new Point2D.Double(-1, -1);
        Point2D c = new Point2D.Double(0, -2);
        Triangle triangle = new Triangle(a, b, c);

        double perimeter = triangle.getPerimeter();
        assertEquals(perimeter, Math.sqrt(8) + Math.sqrt(2) + Math.sqrt(10), 1e-15);
    }

    @Test
    public void canGetSurfaceArea() {
        Point2D a = new Point2D.Double(0, 0);
        Point2D b = new Point2D.Double(1, 0);
        Point2D c = new Point2D.Double(0, 1);
        Triangle triangle = new Triangle(a, b, c);

        double surfaceArea = triangle.getSurfaceArea();
        assertEquals(surfaceArea, 0.5, 1e-15);
    }

    @Test
    public void canGetSurfaceAreaOfIsoscelesTriangle() {
        Point2D a = new Point2D.Double(0, 0);
        Point2D b = new Point2D.Double(2, 0);
        Point2D c = new Point2D.Double(1, 1);
        Triangle triangle = new Triangle(a, b, c);

        double surfaceArea = triangle.getSurfaceArea();
        assertEquals(surfaceArea, 1, 1e-15);
    }

    @Test
    public void canGetSurfaceAreaOfVersatileTriangle() {
        Point2D a = new Point2D.Double(0, 0);
        Point2D b = new Point2D.Double(2, 0);
        Point2D c = new Point2D.Double(0.5, 1);
        Triangle triangle = new Triangle(a, b, c);

        double surfaceArea = triangle.getSurfaceArea();
        assertEquals(surfaceArea, 1, 1e-15);
    }

    @Test
    public void canGetABCAngleOfRectangularTriangle() {
        Point2D a = new Point2D.Double(0, 1);
        Point2D b = new Point2D.Double(0, 0);
        Point2D c = new Point2D.Double(1, 0);
        Triangle triangle = new Triangle(a, b, c);

        double angle = triangle.getABCAngle();
        assertEquals(angle, 90 * Math.PI / 180, 1e-15);
    }

    @Test
    public void canGetABCAsObtuseAngle() {
        Point2D a = new Point2D.Double(1, 0);
        Point2D b = new Point2D.Double(0, 0);
        Point2D c = new Point2D.Double(-1, 1);
        Triangle triangle = new Triangle(a, b, c);

        double angle = triangle.getABCAngle();
        assertEquals(angle, 135 * Math.PI / 180, 1e-15);
    }

    @Test
    public void canGetABCAsAcuteAngle() {
        Point2D a = new Point2D.Double(1, 0);
        Point2D b = new Point2D.Double(0, 0);
        Point2D c = new Point2D.Double(1, 1);
        Triangle triangle = new Triangle(a, b, c);

        double angle = triangle.getABCAngle();
        assertEquals(angle, 45 * Math.PI / 180, 1e-15);
    }

    @Test
    public void canGetBCAAngleOfRectangularTriangle() {
        Point2D a = new Point2D.Double(2, 0);
        Point2D b = new Point2D.Double(1, 1);
        Point2D c = new Point2D.Double(1, 0);
        Triangle triangle = new Triangle(a, b, c);

        double angle = triangle.getBCAAngle();
        assertEquals(angle, 90 * Math.PI / 180, 1e-15);
    }

    @Test
    public void canGetBCAAsObtuseAngle() {
        Point2D b = new Point2D.Double(-1, 1);
        Point2D a = new Point2D.Double(1, 0);
        Point2D c = new Point2D.Double(0, 0);
        Triangle triangle = new Triangle(a, b, c);

        double angle = triangle.getBCAAngle();
        assertEquals(angle, 135 * Math.PI / 180, 1e-15);
    }

    @Test
    public void canGetBCAAsAcuteAngle() {
        Point2D b = new Point2D.Double(1, 1);
        Point2D c = new Point2D.Double(0, 0);
        Point2D a = new Point2D.Double(1, 0);
        Triangle triangle = new Triangle(a, b, c);

        double angle = triangle.getBCAAngle();
        assertEquals(angle, 45 * Math.PI / 180, 1e-15);
    }

    @Test
    public void canGetCABAngleOfRectangularTriangle() {
        Point2D c = new Point2D.Double(1, 1);
        Point2D a = new Point2D.Double(1, 0);
        Point2D b = new Point2D.Double(2, 0);
        Triangle triangle = new Triangle(a, b, c);

        double angle = triangle.getCABAngle();
        assertEquals(angle, 90 * Math.PI / 180, 1e-15);
    }

    @Test
    public void canGetCABAsObtuseAngle() {
        Point2D c = new Point2D.Double(-1, 1);
        Point2D a = new Point2D.Double(0, 0);
        Point2D b = new Point2D.Double(1, 0);
        Triangle triangle = new Triangle(a, b, c);

        double angle = triangle.getCABAngle();
        assertEquals(angle, 135 * Math.PI / 180, 1e-15);
    }

    @Test
    public void canGetCABAsAcuteAngle() {
        Point2D c = new Point2D.Double(1, 1);
        Point2D b = new Point2D.Double(1, 0);
        Point2D a = new Point2D.Double(0, 0);
        Triangle triangle = new Triangle(a, b, c);

        double angle = triangle.getCABAngle();
        assertEquals(angle, 45 * Math.PI / 180, 1e-15);
    }
}

