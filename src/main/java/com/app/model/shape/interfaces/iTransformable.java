package com.app.model.shape.interfaces;

import com.app.model.point.Point;

public interface iTransformable<T extends Point> extends iMovable<T>, iRotatable, iScalable {

}
