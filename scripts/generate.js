const fs = require("fs");
const path = require("path");

if (process.argv.length < 4) {
  console.log("Usage: node generate.js <json|xml|csv> <rows> <outputFile>");
  process.exit(1);
}

const type = process.argv[2];
const rows = Number(process.argv[3]);
const output =
  process.argv[4] || `output/${new Date().getTime()}_${rows}.${type}`;

function rand(min, max) {
  return Math.floor(Math.random() * (max - min + 1) + min);
}

function generateNestedJsonObject(i) {
  return {
    id: i,
    name: `name_${i}`,
    metadata: {
      tags: ["t1", "t2", "t3"],
      attributes: {
        height: rand(150, 200),
        weight: rand(50, 120),
        active: i % 2 === 0,
      },
    },
    history: [
      { event: "created", time: new Date().toISOString() },
      { event: "updated", time: new Date().toISOString() },
    ],
  };
}

function generateNestedXmlRow(i) {
  return `
  <row>
    <id>${i}</id>
    <name>name_${i}</name>
    <metadata>
      <tags>
        <tag>t1</tag>
        <tag>t2</tag>
        <tag>t3</tag>
      </tags>
      <attributes>
        <height>${rand(150, 200)}</height>
        <weight>${rand(50, 120)}</weight>
        <active>${i % 2 === 0}</active>
      </attributes>
    </metadata>
    <history>
      <entry>
        <event>created</event>
        <time>${new Date().toISOString()}</time>
      </entry>
      <entry>
        <event>updated</event>
        <time>${new Date().toISOString()}</time>
      </entry>
    </history>
  </row>
`;
}

function generateCsvRow(i) {
  const height = rand(150, 200);
  const weight = rand(50, 120);
  const active = i % 2 === 0;
  const createdAt = new Date().toISOString();

  return `${i},name_${i},${height},${weight},${active},${createdAt}\n`;
}

async function generateJSON(rows, output) {
  const stream = fs.createWriteStream(output);
  stream.write("[\n");

  for (let i = 1; i <= rows; i++) {
    const obj = generateNestedJsonObject(i);
    stream.write("  " + JSON.stringify(obj, null, 2));
    if (i < rows) stream.write(",");
    stream.write("\n");

    if (i % 5000 === 0) await new Promise((r) => setTimeout(r));
  }

  stream.write("]\n");
  stream.end();
}

async function generateXML(rows, output) {
  const stream = fs.createWriteStream(output);
  stream.write("<rows>\n");

  for (let i = 1; i <= rows; i++) {
    stream.write(generateNestedXmlRow(i));

    if (i % 5000 === 0) await new Promise((r) => setTimeout(r));
  }

  stream.write("</rows>\n");
  stream.end();
}

async function generateCSV(rows, output) {
  const stream = fs.createWriteStream(output);
  stream.write("id,name,height,weight,active,createdAt\n");

  for (let i = 1; i <= rows; i++) {
    stream.write(generateCsvRow(i));

    if (i % 5000 === 0) await new Promise((r) => setTimeout(r));
  }

  stream.end();
}

(async () => {
  console.log(`Generating ${rows} ${type.toUpperCase()} rows...`);

  if (type === "json") {
    await generateJSON(rows, output);
  } else if (type === "xml") {
    await generateXML(rows, output);
  } else if (type === "csv") {
    await generateCSV(rows, output);
  } else {
    console.error("Unknown type:", type);
    process.exit(1);
  }

  console.log("Done:", path.resolve(output));
})();
