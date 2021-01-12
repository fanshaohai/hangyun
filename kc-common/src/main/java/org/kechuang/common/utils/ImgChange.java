package org.kechuang.common.utils;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.io.*;

/**
 *
 * @ClassName: ImgChange2
 * @Description: 图片处理工具类
 * @author tzl
 * @date 2018年3月26日
 */
@Component
public class ImgChange {
	// 设置图片高
	private int changeHeight;
	// 设置图片宽
	private int changeWeight;
	// 水印文件
	private String waterImagePath;
	// 水印文件
	private InputStream waterImageFile;

	// 指定大小把图片进行缩放(会遵循原图高宽比例)-----第一个参数可以是URL，String，File，InputStream中的任何一种，下面方法相同,第二个参数可以是File，String----具体看源码！！
	public void handPicByAutoSize(InputStream inputStream, File targetFile) throws IOException {
		Thumbnails.of(inputStream).size(changeWeight, changeHeight).toFile(targetFile);
	}

	// 按照比例进行缩小和放大(scale如0.5f，值如果在0-1之间则是缩小，如果大于1，则是放大)
	public void handlePicByProportion(InputStream inputStream, File targetFile, float scale) throws IOException {
		Thumbnails.of(inputStream).scale(scale).toFile(targetFile);
	}

	// 不按照比例就按指定的大小进行缩放(图片可能会失真，不会遵循原图高宽比例)
	public void handerPicBySize(InputStream inputStream, File targetFile) throws IOException {
		Thumbnails.of(inputStream).size(changeWeight, changeHeight).keepAspectRatio(false).toFile(targetFile);
		// Thumbnails.of(inputStream).forceSize(changeWeight,changeHeight).toFile(targetFile);
	}

	// 先指定大小把图片进行缩放再旋转图片，rotate(角度,如90),正数则为顺时针，负数则为逆时针
	public void handerPicByRotate(InputStream inputStream, File targetFile, int rotate) throws IOException {
		Thumbnails.of(inputStream).size(changeWeight, changeHeight).rotate(rotate).toFile(targetFile);
	}

	// 图片尺寸不发生变化，通过降低图片质量进行压缩,压缩图片文件大小outputQuality实现,参数1为最高质量,quality为图像质量(如0.25f)
	// 可以实现先指定比例缩小再调节图像质量
	public void handerPicByCompress(InputStream inputStream, File targetFile, float quality) throws IOException {
		Thumbnails.of(inputStream).scale(1f).outputQuality(quality).toFile(targetFile);
	}

	// 指定图片位置进行裁剪，大小可自行设置，图片中心300*300的区域,Positions.CENTER表示中心，还有许多其他位置可选
	public void handerPicByCut(InputStream inputStream, File targetFile) throws IOException {
		Thumbnails.of(inputStream).sourceRegion(Positions.CENTER, 300, 300).size(300, 300).toFile(targetFile);
		// Thumbnails.of(inputStream).sourceRegion(0, 0, 200,
		// 200).size(300,300).toFile(targetFile);
	}

	// 给图片加水印，watermark(位置，水印图，透明度)Positions.CENTER表示加在中间
	public void handerPicByWatermark(InputStream inputStream, File targetFile) throws IOException {
		Thumbnails.of(inputStream).size(400, 400).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(waterImagePath)), 0.5f).outputQuality(0.8f)
				.toFile(targetFile);
	}

	// 转换图片格式，如jpg转成png图片
	public void handerPicByConversionFormat() {

	}

	// 输出成文件流
	public void handerPicByOutputStream() {

	}

	// 输出成BufferedImage
	public void handerPicByBufferedImage() {

	}

	/**
	 * 设置缩略图的长或者高
	 * @param changeHeight
	 * @param changeWeight
	 */
	public void setChangeHeightAndWeight(int changeHeight, int changeWeight) {
		this.changeHeight = changeHeight;
		this.changeWeight = changeWeight;
	}

	/**
	 * 设置水印路径
	 * @param waterImagePath
	 */
	public void setWaterImagePath(String waterImagePath) {
		this.waterImagePath = waterImagePath;
	}

}
