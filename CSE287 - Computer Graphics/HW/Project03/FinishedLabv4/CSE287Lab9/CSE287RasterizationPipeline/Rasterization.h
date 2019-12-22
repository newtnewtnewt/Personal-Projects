#pragma once

#include "Defines.h"
#include "FrameBuffer.h" 
#include "PerFragment.h"
#include "VertexData.h"

// Takes vertices in sequential pairs and draws a line segment between each pair
void rasterizeLines(const std::vector<VertexData> & vertices);

// Draws one or more wire frame triangles if there are at least three vertices
void rasterizeWireFrameTriangles(const std::vector<VertexData> & vertices);

// Draws one or more solid triangles if there are at least three vertices
void rasterizeFilledTriangles(const std::vector<VertexData> & vertices);
