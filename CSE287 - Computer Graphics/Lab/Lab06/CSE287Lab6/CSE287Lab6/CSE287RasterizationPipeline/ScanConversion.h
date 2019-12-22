#pragma once

#include "Defines.h"
#include "FrameBuffer.h"



// Swaps two points
void swap(dvec3 &p1, dvec3 &p2);

// Draws a line segment if there are at least two vertices
void drawLine(std::vector<dvec3> vertices, color rgba);

// Draws a wire frame if there are at least three vertices
void drawWireTriangle(std::vector<dvec3> vertices, color rgba);

// Draws a filled triangle if there are at least three vertices
void drawFilledTriangle(std::vector<dvec3> vertices, color rgba);

// Draws a wire frame rectangle bounded by xMin, yMin, xMax and yMax
void drawWireRectangle(float xMin, float yMin, float xMax, float yMax, color rgba);

// Draws a filled rectangle bounded by xMin, yMin, xMax and yMax
void drawFilledRectangle(float xMin, float yMin, float xMax, float yMax, color rgba);

// Draws a filled in polygon if there are at least three vertices. 
// Polygon must be convex.
void drawFilledPolygon(std::vector<dvec3> vertices, color rgba);