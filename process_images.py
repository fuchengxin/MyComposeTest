#!/usr/bin/env python3
"""
图片处理脚本
功能：
1. 给图片增加1-2像素的透明边框
2. 微调图片中的颜色（调整1-2个像素的色值）
"""

import os
from PIL import Image

def add_transparent_border(input_path, output_path, border_width=2):
    """
    给图片增加透明边框
    :param input_path: 输入图片路径
    :param output_path: 输出图片路径
    :param border_width: 边框宽度（像素）
    """
    try:
        # 打开图片
        img = Image.open(input_path).convert('RGBA')
        
        # 计算新图片的尺寸
        new_width = img.width + 2 * border_width
        new_height = img.height + 2 * border_width
        
        # 创建新的透明图片
        new_img = Image.new('RGBA', (new_width, new_height), (0, 0, 0, 0))
        
        # 将原图片粘贴到新图片的中心
        new_img.paste(img, (border_width, border_width))
        
        # 保存图片
        new_img.save(output_path, 'PNG')
        print(f"已处理: {input_path} -> {output_path}")
    except Exception as e:
        print(f"处理图片时出错: {input_path}, 错误: {e}")

def adjust_color(input_path, output_path, target_color, adjustment=1):
    """
    微调图片中的颜色
    :param input_path: 输入图片路径
    :param output_path: 输出图片路径
    :param target_color: 目标颜色 (R, G, B)
    :param adjustment: 调整值（1-2）
    """
    try:
        # 打开图片
        img = Image.open(input_path).convert('RGBA')
        
        # 获取图片数据
        data = list(img.getdata())
        
        # 微调颜色
        new_data = []
        for item in data:
            # 如果像素颜色与目标颜色匹配（考虑透明度）
            if item[:3] == target_color:
                # 微调颜色，每个通道减少adjustment值，确保不小于0
                new_r = max(0, item[0] - adjustment)
                new_g = max(0, item[1] - adjustment)
                new_b = max(0, item[2] - adjustment)
                # 替换为新颜色，保留透明度
                new_data.append((new_r, new_g, new_b, item[3]))
            else:
                new_data.append(item)
        
        # 创建新图片
        new_img = Image.new('RGBA', img.size)
        new_img.putdata(new_data)
        
        # 保存图片
        new_img.save(output_path, 'PNG')
        print(f"已处理: {input_path} -> {output_path}")
    except Exception as e:
        print(f"处理图片时出错: {input_path}, 错误: {e}")

def hex_to_rgb(hex_color):
    """
    将十六进制颜色转换为RGB元组
    :param hex_color: 十六进制颜色字符串（如 #FFFFFF）
    :return: RGB元组 (R, G, B)
    """
    hex_color = hex_color.strip('#')
    return tuple(int(hex_color[i:i+2], 16) for i in (0, 2, 4))

def rgb_to_hex(rgb_color):
    """
    将RGB元组转换为十六进制颜色字符串
    :param rgb_color: RGB元组 (R, G, B)
    :return: 十六进制颜色字符串（如 #FFFFFF）
    """
    return '#{:02x}{:02x}{:02x}'.format(*rgb_color)

def main():
    # 定义图片目录
    image_dirs = [
        'app/src/main/res/drawable-xxhdpi',
        'base/src/main/res/drawable-xxhdpi'
    ]
    
    # 选择操作类型
    print("请选择操作类型:")
    print("1. 给图片增加透明边框")
    print("2. 微调图片中的颜色")
    choice = input("请输入数字 (1/2): ")
    
    if choice == '1':
        # 增加透明边框
        border_width = int(input("请输入边框宽度 (1-2): "))
        border_width = max(1, min(2, border_width))  # 确保宽度在1-2之间
        
        for img_dir in image_dirs:
            abs_dir = os.path.join(os.getcwd(), img_dir)
            if os.path.exists(abs_dir):
                print(f"处理目录: {abs_dir}")
                for filename in os.listdir(abs_dir):
                    if filename.endswith(('.png', '.jpg', '.jpeg')):
                        input_path = os.path.join(abs_dir, filename)
                        output_path = os.path.join(abs_dir, filename)  # 覆盖原文件
                        add_transparent_border(input_path, output_path, border_width)
    
    elif choice == '2':
        # 微调颜色
        # 默认微调白色 (#FFFFFF)，每个通道减少1
        target_color = (255, 255, 255)  # #FFFFFF
        adjustment = 1
        
        print(f"默认微调颜色: #FFFFFF -> {rgb_to_hex((255-adjustment, 255-adjustment, 255-adjustment))}")
        confirm = input("是否使用默认颜色微调? (y/n): ")
        
        if confirm.lower() != 'y':
            # 自定义目标颜色
            hex_color = input("请输入要微调的颜色 (如 #FFFFFF): ")
            try:
                target_color = hex_to_rgb(hex_color)
            except:
                print("颜色格式错误，使用默认颜色")
            
            # 自定义调整值
            adj = input("请输入调整值 (1-2): ")
            try:
                adjustment = int(adj)
                adjustment = max(1, min(2, adjustment))  # 确保调整值在1-2之间
            except:
                print("调整值错误，使用默认值 1")
        
        print(f"微调颜色: {rgb_to_hex(target_color)} -> {rgb_to_hex((max(0, target_color[0]-adjustment), max(0, target_color[1]-adjustment), max(0, target_color[2]-adjustment)))}")
        
        for img_dir in image_dirs:
            abs_dir = os.path.join(os.getcwd(), img_dir)
            if os.path.exists(abs_dir):
                print(f"处理目录: {abs_dir}")
                for filename in os.listdir(abs_dir):
                    if filename.endswith(('.png', '.jpg', '.jpeg')):
                        input_path = os.path.join(abs_dir, filename)
                        output_path = os.path.join(abs_dir, filename)  # 覆盖原文件
                        adjust_color(input_path, output_path, target_color, adjustment)
    
    else:
        print("无效的选择")

if __name__ == "__main__":
    main()
