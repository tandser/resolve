package ru.tandser.resolve;

import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.design.JRDesignStyle;

public enum StyleTypes {

    NORMAL("Sans_Normal", "DejaVu Sans"), BOLD("Sans_Bold", "DejaVu Sans"), ITALIC("Sans_Italic", "DejaVu Sans");

    private String name;
    private String font;

    StyleTypes(String name, String font) {
        this.name = name;
        this.font = font;
    }

    public static JRStyle n() {
        JRDesignStyle style = new JRDesignStyle();

        style.setName(StyleTypes.NORMAL.getName());
        style.setDefault(true);
        style.setFontName(StyleTypes.NORMAL.getFont());
        style.setFontSize(12f);
        style.setPdfFontName("Helvetica");
        style.setPdfEncoding("Cp1252");
        style.setPdfEmbedded(false);

        return style;
    }

    public static JRStyle b() {
        JRDesignStyle style = new JRDesignStyle();

        style.setName(StyleTypes.BOLD.getName());
        style.setFontName(StyleTypes.BOLD.getFont());
        style.setFontSize(12f);
        style.setBold(true);
        style.setPdfFontName("Helvetica-Bold");
        style.setPdfEncoding("Cp1252");
        style.setPdfEmbedded(false);

        return style;
    }

    public static JRStyle i() {
        JRDesignStyle style = new JRDesignStyle();

        style.setName(StyleTypes.ITALIC.getName());
        style.setFontName(StyleTypes.ITALIC.getFont());
        style.setFontSize(12f);
        style.setItalic(true);
        style.setPdfFontName("Helvetica-Oblique");
        style.setPdfEncoding("Cp1252");
        style.setPdfEmbedded(false);

        return style;
    }

    //<editor-fold desc="Getters">

    public String getName() {
        return name;
    }

    public String getFont() {
        return font;
    }

    //</editor-fold>
}