public class MinMaxChallenge {
    public static void main(String[] args) {
        getBucketCount(7.25, 4.3,2.35);
    }
    public static int getBucketCount(double width, double height, double areaPerBucket, int extraBuckets){

          if( width <= 0 || height <= 0 || areaPerBucket <=0 || extraBuckets < 0) {
              return -1;
          }

          double wallArea = width * height;
          int backetToBuy = 0;

          if(extraBuckets == 0) {
              backetToBuy = (int)Math.ceil(wallArea / areaPerBucket);
          } else {
              backetToBuy = (int)Math.ceil((wallArea - (areaPerBucket * extraBuckets)) / areaPerBucket);
          }

        return backetToBuy;
    }
    public static int getBucketCount(double width, double height, double areaPerBucket){

        if( width <= 0 || height <= 0 || areaPerBucket <=0) {
            return -1;
        }
        double wallArea = width * height;
        int backetToBuy = (int)Math.ceil(wallArea / areaPerBucket);

        return backetToBuy;
    }
    public static int getBucketCount(double area, double areaPerBucket){

        if( area <= 0 || areaPerBucket <=0) {
            return -1;
        }
       int backetToBuy = (int)Math.ceil(area / areaPerBucket);

        return backetToBuy;
    }
}
