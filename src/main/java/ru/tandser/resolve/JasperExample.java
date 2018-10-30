package ru.tandser.resolve;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.fill.JRGzipVirtualizer;
import net.sf.jasperreports.engine.type.*;
import org.apache.commons.io.FileUtils;
import ru.tandser.resolve.model.User;

import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class JasperExample {

    private static final String NAME_REPORT = "JasperReport";

    public static void main(String[] args) throws Exception {
        JasperDesign design = createDesign();
        JasperReport report = compile(design);

        jrxml(report, Paths.get("jrxml.jrxml"));

        byte[] pdf = JasperRunManager.runReportToPdf(
                report,
                parameters(),
                new JRBeanCollectionDataSource(DataGenerator.userCollection(50)));

        FileUtils.writeByteArrayToFile(Paths.get("pdf.pdf").toFile(), pdf);

//        JasperPrint print = JasperFillManager.fillReport(
//                report,
//                parameters(),
//                new JRBeanCollectionDataSource(DataGenerator.userCollection(5)));
//
//        pdf(print, Paths.get("pdf.pdf"));
    }

    public static JasperDesign createDesign() {
        try {
            JasperDesign design = new JasperDesign();

            design.setName(NAME_REPORT);

            design.setColumnWidth(design.getPageWidth() - design.getLeftMargin() - design.getRightMargin());

            design.addStyle(StyleTypes.n());
            design.addStyle(StyleTypes.b());
            design.addStyle(StyleTypes.i());

            design.addField(field(User.NAME,      String.class));
            design.addField(field(User.SURNAME,   String.class));
            design.addField(field(User.BIRTHDATE, LocalDate.class));

            design.addSortField(new JRDesignSortField(
                    User.BIRTHDATE,
                    SortFieldTypeEnum.FIELD,
                    SortOrderEnum.ASCENDING));

            design.addImport(LocalDate.class.getName());
            design.addImport(Period.class.getName());

            design.addVariable(age());

            design.setFilterExpression(notChild());

            design.setPageHeader(header(design));

            ((JRDesignSection) design.getDetailSection()).addBand(detail(design));

            return design;
        } catch (JRException exc) {
            throw new RuntimeException(exc);
        }
    }


    public static JRField field(String name, Class<?> type) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(type);

        JRDesignField field = new JRDesignField();

        field.setName(name);
        field.setValueClass(type);

        return field;
    }

    public static void jrxml(JRReport report, Path path) {
        Objects.requireNonNull(report);
        Objects.requireNonNull(path);

        try {
            JasperCompileManager.writeReportToXmlFile(report, path.toString());
        } catch (JRException exc) {
            throw new RuntimeException(exc);
        }
    }

    public static void pdf(JasperPrint print, Path path) {
        Objects.requireNonNull(print);
        Objects.requireNonNull(path);

        try {
            JasperExportManager.exportReportToPdfFile(print, path.toString());
        } catch (JRException exc) {
            throw new RuntimeException(exc);
        }
    }

    public static JasperReport compile(JasperDesign design) {
        try {
            return JasperCompileManager.compileReport(design);
        } catch (JRException exc) {
            throw new RuntimeException(exc);
        }
    }

    private static Map<String, Object> parameters() {
        Map<String, Object> parameters = new HashMap<>();

        parameters.put(JRParameter.REPORT_VIRTUALIZER, new JRGzipVirtualizer(3));

        return parameters;
    }

    private static JRBand header(JasperDesign design) {
        JRDesignBand  band  = new JRDesignBand();
        JRDesignFrame frame = new JRDesignFrame();

        band.setHeight(20);
        band.addElement(frame);

        frame.setX(0);
        frame.setY(5);
        frame.setWidth(design.getColumnWidth());
        frame.setHeight(15);
        frame.setMode(ModeEnum.OPAQUE);

        JRStyle bold = design.getStylesMap().get(StyleTypes.BOLD.getName());

        frame.addElement(staticText("#",         bold, 0,   HorizontalTextAlignEnum.CENTER));
        frame.addElement(staticText("Name",      bold, 111, HorizontalTextAlignEnum.CENTER));
        frame.addElement(staticText("Surname",   bold, 222, HorizontalTextAlignEnum.CENTER));
        frame.addElement(staticText("Age",       bold, 333, HorizontalTextAlignEnum.CENTER));
        frame.addElement(staticText("Birthdate", bold, 444, HorizontalTextAlignEnum.CENTER));

        JRDesignLine line = new JRDesignLine();

        line.setX(0);
        line.setY(19);
        line.setWidth(frame.getWidth());
        line.setHeight(0);
        line.setForecolor(Color.GRAY);

        band.addElement(line);

        return band;
    }

    private static JRBand detail(JasperDesign design) {
        JRDesignBand band = new JRDesignBand();

        band.setHeight(20);

        JRStyle normal = design.getStylesMap().get(StyleTypes.NORMAL.getName());

        band.addElement(textField("$V{REPORT_COUNT}", normal, 0,   HorizontalTextAlignEnum.CENTER));
        band.addElement(textField("$F{name}",         normal, 111, HorizontalTextAlignEnum.CENTER));
        band.addElement(textField("$F{surname}",      normal, 222, HorizontalTextAlignEnum.CENTER));
        band.addElement(textField("$V{age}",          normal, 333, HorizontalTextAlignEnum.CENTER));
        band.addElement(textField("$F{birthdate}",    normal, 444, HorizontalTextAlignEnum.CENTER));

        return band;
    }

    private static JRStaticText staticText(String name, JRStyle style, int x, HorizontalTextAlignEnum align) {
        JRDesignStaticText staticText = new JRDesignStaticText();

        staticText.setX(x);
        staticText.setY(0);
        staticText.setWidth(111);
        staticText.setHeight(15);
        staticText.setHorizontalTextAlign(align);
        staticText.setVerticalTextAlign(VerticalTextAlignEnum.MIDDLE);
        staticText.setStyle(style);
        staticText.setText(name);

        return staticText;
    }

    private static JRTextField textField(String expression, JRStyle style, int x, HorizontalTextAlignEnum align) {
        JRDesignTextField textField = new JRDesignTextField();

        textField.setX(x);
        textField.setY(4);
        textField.setWidth(111);
        textField.setHeight(15);
        textField.setHorizontalTextAlign(align);
        textField.setVerticalTextAlign(VerticalTextAlignEnum.MIDDLE);
        textField.setStyle(style);
        textField.setExpression(new JRDesignExpression(expression));

        return textField;
    }

    private static JRDesignVariable age() {
        JRDesignVariable age = new JRDesignVariable();

        age.setName("age");
        age.setValueClass(Integer.class);
        age.setExpression(new JRDesignExpression("Period.between($F{birthdate}, LocalDate.now()).getYears()"));

        return age;
    }

    private static JRExpression notChild() {
        return new JRDesignExpression("$V{age} >= 13");
    }
}