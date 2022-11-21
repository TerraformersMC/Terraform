package com.terraformersmc.terraform.shapes.api;

import org.joml.Quaterniondc;
import org.joml.Quaternionfc;

public interface Quaternion {

    static Quaternion of(double ofW, double ofI, double ofJ, double ofK) {
        return new Quaternion() {
            private double w = ofW;
            private double i = ofI;
            private double j = ofJ;
            private double k = ofK;

            @Override
            public double getW() {
                return w;
            }

            @Override
            public double getI() {
                return i;
            }

            @Override
            public double getJ() {
                return j;
            }

            @Override
            public double getK() {
                return k;
            }

            @Override
            public void setW(double w) {
                this.w = w;
            }

            @Override
            public void setI(double i) {
                this.i = i;
            }

            @Override
            public void setJ(double j) {
                this.j = j;
            }

            @Override
            public void setK(double k) {
                this.k = k;
            }
        };
    }

    //TODO: Revisit this
    static Quaternion of(double x, double y, double z, boolean degrees) {
        if (degrees) {
            x *= 0.017453292F;
            y *= 0.017453292F;
            z *= 0.017453292F;
        }

        double f = Math.sin(0.5F * x);
        double g = Math.cos(0.5F * x);
        double h = Math.sin(0.5F * y);
        double i = Math.cos(0.5F * y);
        double j = Math.sin(0.5F * z);
        double k = Math.cos(0.5F * z);

        double xNew = f * i * k + g * h * j;
        double yNew = g * h * k - f * i * j;
        double zNew = f * h * k + g * i * j;
        double wNew = g * i * k - f * h * j;

        return of(wNew, xNew, yNew, zNew);
    }

	static Quaternion of(Quaterniondc q) {
		return of(q.w(), q.x(), q.y(), q.z());
	}

	static Quaternion of(Quaternionfc q) {
		return of(q.w(), q.x(), q.y(), q.z());
	}

    static Quaternion of(Quaternion q) {
        return of(q.getW(), q.getI(), q.getJ(), q.getK());
    }

    default Quaternion copy() {
        return Quaternion.of(this);
    }

    double getW();

    double getI();

    double getJ();

    double getK();

    void setW(double w);

    void setI(double i);

    void setJ(double j);

    void setK(double k);

    default Quaternion multiply(Quaternion multiplier) {
        double newW = (this.getW() * multiplier.getW()) - (this.getI() * multiplier.getI()) - (this.getJ() * multiplier.getJ()) - (this.getK() * multiplier.getK());
        double newI = (this.getW() * multiplier.getI()) + (this.getI() * multiplier.getW()) + (this.getJ() * multiplier.getK()) - (this.getK() * multiplier.getJ());
        double newJ = (this.getW() * multiplier.getJ()) - (this.getI() * multiplier.getK()) + (this.getJ() * multiplier.getW()) + (this.getK() * multiplier.getI());
        double newK = (this.getW() * multiplier.getK()) + (this.getI() * multiplier.getJ()) - (this.getJ() * multiplier.getI()) + (this.getK() * multiplier.getW());
        this.setW(newW);
        this.setI(newI);
        this.setJ(newJ);
        this.setK(newK);
        return this;
    }

    default Quaternion conjugate() {
        return Quaternion.of(this.getW(), -this.getI(), -this.getJ(), -this.getK());
    }

    default Position toPosition() {
        return Position.of(this.getI(), this.getJ(), this.getK());
    }

    static Quaternion of(Position pos) {
        return of(0, pos.getX(), pos.getY(), pos.getZ());
    }
}
