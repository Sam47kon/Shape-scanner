package com.app.model.shape.interfaces;

import com.app.model.point.Point;

public interface ITransformable<T extends Point> extends IMovable<T>, IRotatable, IScalable {

}
